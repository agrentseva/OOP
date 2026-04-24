package ru.nsu.ga.grentseva.checker;

import ru.nsu.ga.grentseva.checker.dsl.ConfigDslParser;
import ru.nsu.ga.grentseva.checker.model.Config;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;
import ru.nsu.ga.grentseva.checker.runner.HTMLReportGenerator;
import ru.nsu.ga.grentseva.checker.runner.PipelineRunner;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
            System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8.name()));
        } catch (Exception ignored) {}

        if (args.length == 0 || !args[0].equals("check")) {
            System.err.println("Использование: java -jar app.jar check [config.groovy]");
            return;
        }

        String configFileName = args.length > 1 ? args[1] : "oop-check.groovy";

        try {
            Path configPath = Paths.get(configFileName).toAbsolutePath();
            Config config = ConfigDslParser.parse(configPath);

            PipelineRunner runner = new PipelineRunner();
            Map<Submission, SubmissionResult> results = runner.run(config);

            new HTMLReportGenerator().generate(config, results, "report.html");

            System.out.println("✔ Проверка завершена. Отчет: report.html");

        } catch (Exception e) {
            System.err.println("Ошибка:");
            e.printStackTrace();
        }
    }
}