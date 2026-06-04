package ru.nsu.ga.grentseva.checker.service.pipeline;

import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Student;
import ru.nsu.ga.grentseva.checker.model.Submission;
import ru.nsu.ga.grentseva.checker.model.SubmissionResult;
import ru.nsu.ga.grentseva.checker.model.Task;

import java.io.File;
import java.util.Map;

public class StudentProcessor {

    private final TaskChecker taskChecker = new TaskChecker();

    public void process(CourseConfig config, Student student, File workspace,
                        Map<Submission, SubmissionResult> results) {
        File repositoryDirectory = new File(workspace, student.getGithubId());

        for (Submission submission : config.getSubmissions()) {
            if (!submission.getStudentId().equals(student.getGithubId())) {
                continue;
            }

            Task task = findTask(config, submission.getTaskId());
            if (task == null) {
                continue;
            }

            SubmissionResult result = taskChecker.checkTask(task, submission,
                    repositoryDirectory, config.getSettings());

            results.put(submission, result);
        }
    }

    private Task findTask(CourseConfig config, String taskId) {
        for (Task task : config.getTasks()) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }
}