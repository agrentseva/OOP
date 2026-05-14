package ru.nsu.ga.grentseva.checker;

import ru.nsu.ga.grentseva.checker.dsl.ConfigDslParser;

import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;

import ru.nsu.ga.grentseva.checker.service.HTMLReportGenerator;
import ru.nsu.ga.grentseva.checker.service.PipelineRunner;

import java.io.PrintStream;

import java.nio.charset.StandardCharsets;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

public class Main {

    private static final String DEFAULT_CONFIG = "oop-check.groovy";
    private static final String REPORT_FILE = "report.html";

    public static void main(String[] args) {
        setupUtf8();

        if (!isValidCommand(args)) {
            printUsage();
            return;
        }

        String configFile = getConfigFileName(args);

        try {
            Path configPath = Paths.get(configFile).toAbsolutePath();
            CourseConfig config = ConfigDslParser.parse(configPath);

            PipelineRunner pipelineRunner = new PipelineRunner();

            Map<Submission, SubmissionResult> results = pipelineRunner.run(config);

            HTMLReportGenerator reportGenerator = new HTMLReportGenerator();
            reportGenerator.generate(config, results, REPORT_FILE);

            System.out.println();
            System.out.println("Проверка завершена");

            System.out.println("HTML отчет: " + REPORT_FILE);
        } catch (Exception e) {
            System.err.println();
            System.err.println("Ошибка работы программы:");
            e.printStackTrace();
        }
    }
    
    private static void setupUtf8() {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
            System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8.name()));
        } catch (Exception ignored) {
        }
    }

    private static boolean isValidCommand(String[] args) {
        return args.length > 0 && args[0].equals("check");
    }

    private static String getConfigFileName(String[] args) {
        if (args.length > 1) {
            return args[1];
        }

        return DEFAULT_CONFIG;
    }

    private static void printUsage() {
        System.out.println();
        System.out.println("Использование:");
        System.out.println("java -jar checker.jar check [config.groovy]");
        System.out.println();
        System.out.println("Пример:");
        System.out.println("java -jar checker.jar check oop-check.groovy");
    }
}