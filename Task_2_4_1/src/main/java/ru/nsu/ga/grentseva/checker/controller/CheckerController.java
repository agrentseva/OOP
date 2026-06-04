package ru.nsu.ga.grentseva.checker.controller;

import ru.nsu.ga.grentseva.checker.dsl.ConfigDslParser;
import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;
import ru.nsu.ga.grentseva.checker.service.logging.Logger;
import ru.nsu.ga.grentseva.checker.service.pipeline.PipelineRunner;
import ru.nsu.ga.grentseva.checker.service.report.HTMLReportGenerator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CheckerController {

    private static final String DEFAULT_CONFIG = "oop-check.groovy";
    private static final String REPORT_FILE = "report.html";

    private final PipelineRunner pipelineRunner = new PipelineRunner();
    private final HTMLReportGenerator reportGenerator = new HTMLReportGenerator();
    private final Logger logger = new Logger();

    public void run(String[] args) {
        if (!isValidCommand(args)) {
            logger.printUsage();
            return;
        }

        String configFile = getConfigFileName(args);

        try {
            logger.applicationStarted();

            Path configPath = Paths.get(configFile).toAbsolutePath();
            CourseConfig config = ConfigDslParser.parse(configPath);

            Map<Submission, SubmissionResult> results = pipelineRunner.run(config);

            reportGenerator.generate(config, results, REPORT_FILE);

            logger.applicationFinished();
            logger.htmlReportGenerated(REPORT_FILE);

        } catch (Exception e) {
            logger.applicationError(e);
        }
    }

    private boolean isValidCommand(String[] args) {
        return args.length > 0 && args[0].equals("check");
    }

    private String getConfigFileName(String[] args) {
        if (args.length > 1) {
            return args[1];
        }
        return DEFAULT_CONFIG;
    }
}