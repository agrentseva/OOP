package ru.nsu.ga.grentseva.checker.runner;

import ru.nsu.ga.grentseva.checker.model.*;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class PipelineRunner {

    private final GitService gitService = new GitService();
    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    public Map<Submission, SubmissionResult> run(Config config) {
        Map<Submission, SubmissionResult> results = new ConcurrentHashMap<>();

        File workspace = new File("checker_workspace");
        if (!workspace.exists()) workspace.mkdirs();

        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Future<?>> futures = new ArrayList<>();

        Map<Student, List<Submission>> map = new HashMap<>();

        for (Group g : config.getGroups()) {
            for (Student s : g.getStudents()) {
                map.put(s, new ArrayList<>());
            }
        }

        for (Submission sub : config.getSubmissions()) {
            config.getGroups().stream()
                    .flatMap(g -> g.getStudents().stream())
                    .filter(s -> s.getGithubId().equals(sub.getStudentId()))
                    .findFirst()
                    .ifPresent(s -> map.get(s).add(sub));
        }

        for (var entry : map.entrySet()) {
            futures.add(pool.submit(() ->
                    processStudent(config, entry.getKey(), entry.getValue(), workspace, results)
            ));
        }

        waitAll(futures);
        pool.shutdown();

        return results;
    }

    private void processStudent(Config config,
                                Student student,
                                List<Submission> submissions,
                                File workspace,
                                Map<Submission, SubmissionResult> results) {

        File repoDir = new File(workspace, student.getGithubId());

        try {
            gitService.cloneOrUpdate(student.getRepoUrl(), repoDir);
        } catch (Exception e) {
            return;
        }

        for (Submission sub : submissions) {
            Task task = findTask(config, sub.getTaskId());
            if (task == null) continue;

            SubmissionResult result = runTask(task, sub, repoDir, config.getSettings());
            results.put(sub, result);
        }
    }

    private SubmissionResult runTask(Task task, Submission submission, File repoDir, Settings settings) {

        SubmissionResult result = new SubmissionResult();
        result.setBonus(submission.getBonus());

        File taskDir = findTaskDir(repoDir, task.getId());
        if (taskDir == null) return result;

        boolean buildOk = runGradle(taskDir, 0, "build");
        result.setCompileSuccess(buildOk);

        int passed = 0, failed = 0, skipped = 0;

        if (buildOk) {

            result.setJavadocPassed(runGradle(taskDir, 0, "javadoc"));

            boolean styleOk = true;
            try {
                styleOk = runGradle(taskDir, 0, "checkstyle")
                        || runGradle(taskDir, 0, "checkstyleMain");

                if (!styleOk) {
                    int errors = runCheckstyle(taskDir);
                    result.setStyleErrors(errors);
                    styleOk = errors == 0 || errors == -1;
                }
            } catch (Exception ignored) {}

            result.setStylePassed(styleOk);

            // 🔥 ГЛАВНОЕ ИСПРАВЛЕНИЕ
            runGradle(taskDir, settings.getTestTimeoutSeconds(), "test");

            int[] t = parseTests(taskDir);

            passed = t[0];
            failed = t[1];
            skipped = t[2];

            // fallback если xml не создался
            if (passed == 0 && failed == 0) {
                failed = 1;
            }
        }

        result.setTestsPassed(passed);
        result.setTestsFailed(failed);
        result.setTestsSkipped(skipped);

        double score = scoreCalculator.calculate(task, passed, failed, submission.getBonus());
        result.setFinalScore(score);

        return result;
    }

    private int[] parseTests(File dir) {

        File folder = new File(dir, "build/test-results/test");
        if (!folder.exists()) return new int[]{0,0,0};

        int p = 0, f = 0, s = 0;

        File[] files = folder.listFiles((d, n) -> n.endsWith(".xml"));
        if (files == null) return new int[]{0,0,0};

        for (File file : files) {
            try {
                var doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(file);

                var root = doc.getDocumentElement();

                int tests = Integer.parseInt(root.getAttribute("tests"));
                int failures = Integer.parseInt(root.getAttribute("failures"));
                int skipped = Integer.parseInt(root.getAttribute("skipped"));

                p += tests - failures - skipped;
                f += failures;
                s += skipped;

            } catch (Exception ignored) {}
        }

        return new int[]{p,f,s};
    }

    // 🔥 ГЛАВНОЕ ИСПРАВЛЕНИЕ ТУТ
    private boolean runGradle(File dir, int timeout, String... args) {

        String gradlew = System.getProperty("os.name").toLowerCase().contains("win")
                ? "gradlew.bat" : "./gradlew";

        File file = new File(dir, gradlew);
        if (!file.exists()) return false;

        List<String> command = new ArrayList<>();
        command.add(file.getAbsolutePath());
        command.add("--no-daemon");
        command.add("-q");
        command.addAll(Arrays.asList(args));

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(dir);
            pb.environment().put("JAVA_HOME", System.getProperty("java.home"));

            Process p = pb.start();

            if (timeout > 0) {
                if (!p.waitFor(timeout, TimeUnit.SECONDS)) {
                    p.destroyForcibly();
                    return false;
                }
                return p.exitValue() == 0;
            } else {
                return p.waitFor() == 0;
            }

        } catch (Exception e) {
            return false;
        }
    }

    private int runCheckstyle(File dir) throws Exception {

        File src = new File(dir, "src/main/java");
        if (!src.exists()) return -1;

        Path cfgPath = Paths.get("lib/google_checks.xml");
        if (!Files.exists(cfgPath)) return -1;

        List<File> files;

        try (Stream<Path> walk = Files.walk(src.toPath())) {
            files = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .toList();
        }

        if (files.isEmpty()) return 0;

        Configuration cfg = ConfigurationLoader.loadConfiguration(
                cfgPath.toString(),
                new PropertiesExpander(System.getProperties())
        );

        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(cfg);

        int errors = checker.process(files);
        checker.destroy();

        return errors;
    }

    private File findTaskDir(File repo, String id) {

        if (!repo.exists()) return null;

        String norm = id.toLowerCase().replace("_sem1","").replace("_sem2","").replace("_","");

        for (File f : Objects.requireNonNull(repo.listFiles())) {
            if (f.isDirectory()) {
                String name = f.getName().toLowerCase().replace("-", "").replace("_", "");
                if (name.contains(norm)) return f;
            }
        }

        return repo;
    }

    private Task findTask(Config config, String id) {
        return config.getTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void waitAll(List<Future<?>> list) {
        for (Future<?> f : list) {
            try { f.get(); } catch (Exception ignored) {}
        }
    }
}