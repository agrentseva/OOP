package ru.nsu.ga.grentseva.checker.service.pipeline;

import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Group;
import ru.nsu.ga.grentseva.checker.model.Student;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;
import ru.nsu.ga.grentseva.checker.service.logging.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PipelineRunner {

    private static final int THREADS = 3;

    private final StudentProcessor studentProcessor = new StudentProcessor();
    private final WorkspaceManager workspaceManager = new WorkspaceManager();
    private final RepositoryPreparer repositoryPreparer = new RepositoryPreparer();
    private final Logger logger = new Logger();

    public Map<Submission, SubmissionResult> run(CourseConfig config) {
        try {
            Map<Submission, SubmissionResult> results = new ConcurrentHashMap<>();
            File workspace = workspaceManager.getWorkspace();

            Set<String> preparedRepositories = repositoryPreparer.prepare(config, workspace);

            ExecutorService executor = Executors.newFixedThreadPool(THREADS);
            List<Future<?>> futures = new ArrayList<>();

            for (Group group : config.getGroups()) {
                for (Student student : group.getStudents()) {
                    if (!preparedRepositories.contains(student.getGithubId())) {
                        continue;
                    }

                    Future<?> future = executor.submit(() ->
                            studentProcessor.process(config, student, workspace, results));
                    futures.add(future);
                }
            }

            waitForCompletion(futures);
            executor.shutdown();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ConcurrentHashMap<>();
        }
    }

    private void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                logger.threadError();
            }
        }
    }
}