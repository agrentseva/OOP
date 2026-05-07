package ru.nsu.ga.grentseva.checker.service;

import ru.nsu.ga.grentseva.checker.model.*;

import java.io.FileWriter;
import java.util.Map;

public class HTMLReportGenerator {

    public void generate(CourseConfig config,
                         Map<Submission,
                                 SubmissionResult> results,
                         String outputFile) {

        StringBuilder html = new StringBuilder();

        html.append("""
                <!DOCTYPE html>
                <html lang='ru'>
                <head>
                    <meta charset='UTF-8'>
                    <title>OOP Checker</title>

                    <style>

                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }

                        body {
                            font-family: -apple-system,
                                         BlinkMacSystemFont,
                                         'Segoe UI',
                                         sans-serif;

                            background: #f5f5f7;
                            color: #1d1d1f;

                            padding: 40px;
                        }

                        .container {
                            max-width: 1400px;
                            margin: 0 auto;
                        }

                        .hero {
                            text-align: center;
                            margin-bottom: 80px;
                        }

                        .hero h1 {
                            font-size: 64px;
                            font-weight: 700;
                            margin-bottom: 20px;
                        }

                        .hero p {
                            font-size: 24px;
                            color: #6e6e73;
                        }

                        .task-card {
                            background: white;
                            border-radius: 28px;
                            padding: 32px;
                            margin-bottom: 40px;

                            box-shadow:
                                0 4px 20px rgba(0,0,0,0.06);
                        }

                        .task-title {
                            font-size: 36px;
                            font-weight: 700;
                            margin-bottom: 30px;
                        }

                        table {
                            width: 100%;
                            border-collapse: collapse;
                            table-layout: fixed;
                        }

                        th {
                            text-align: left;
                            font-size: 15px;
                            color: #6e6e73;
                            padding-bottom: 16px;
                            border-bottom: 1px solid #d2d2d7;
                        }

                        td {
                            padding: 18px 0;
                            border-bottom: 1px solid #f0f0f0;
                            font-size: 16px;
                        }
                        
                        th, td {
                            text-align: center;
                        }
                        
                        th:first-child,
                        td:first-child {
                            text-align: left;
                        }

                        tr:last-child td {
                            border-bottom: none;
                        }

                        .student-name {
                            font-weight: 500;
                            width: 360px;
                        }

                        .status-ok {
                            color: #34c759;
                            font-weight: 700;
                            font-size: 20px;
                        }

                        .status-fail {
                            color: #ff3b30;
                            font-weight: 700;
                            font-size: 20px;
                        }

                        .tests {
                            font-weight: 600;
                        }
                        
                        .passed-tests {
                            color: #34c759;
                            font-weight: 700;
                        }
                
                        .failed-tests {
                            color: #ff3b30;
                            font-weight: 700;
                        }
                        
                        .deadline {
                            color: #6e6e73;
                            font-size: 15px;
                            margin-bottom: 24px;
                        }
                        
                        .deadline-ok {
                            color: #34c759;
                            font-weight: 600;
                        }
                
                        .deadline-fail {
                            color: #ff3b30;
                            font-weight: 600;
                        }
                        
                        .deadline-ok,
                        .deadline-fail {
                            display: inline-block;
                            min-width: 70px;
                        }

                        .score {
                            font-weight: 700;
                            color: #0071e3;
                        }

                        .empty {
                            color: #8e8e93;
                        }

                        .summary-card {
                            background: linear-gradient(
                                135deg,
                                #ffffff,
                                #f2f2f2
                            );

                            border-radius: 28px;
                            padding: 32px;
                            margin-top: 60px;

                            box-shadow:
                                0 4px 20px rgba(0,0,0,0.06);
                        }

                        .summary-title {
                            font-size: 36px;
                            font-weight: 700;
                            margin-bottom: 30px;
                        }

                        .percent {
                            font-weight: 700;
                            color: #34c759;
                        }

                    </style>
                </head>
                <body>
                <div class='container'>

                <div class='hero'>
                    <h1>OOP Checker</h1>
                    <p>Автоматическая проверка лабораторных работ</p>
                </div>
                """);

        
        for (Task task : config.getTasks()) {

            html.append("<div class='task-card'>");

            html.append("<div class='task-title'>")
                    .append(task.getId())
                    .append(" • ")
                    .append(task.getName())
                    .append("</div>");

            html.append("<div class='deadline'>");

            html.append("Soft deadline: ")
                    .append(task.getSoftDeadline());

            html.append(" • Hard deadline: ")
                    .append(task.getHardDeadline());

            html.append("</div>");

            html.append("<table>");

            html.append("""
                    <tr>
                        <th>Студент</th>
                        <th>Compile</th>
                        <th>Javadoc</th>
                        <th>Style</th>
                        <th>Soft DL</th>
                        <th>Hard DL</th>
                        <th>Tests</th>
                        <th>Score</th>
                    </tr>
                    """);

            for (Group group : config.getGroups()) {

                for (Student student : group.getStudents()) {

                    Submission submission =
                            findSubmission(config,
                                    student,
                                    task);

                    SubmissionResult result =
                            submission == null
                                    ? null
                                    : results.get(submission);

                    html.append("<tr>");

                    html.append("<td class='student-name'>")
                            .append(student.getFullName())
                            .append("</td>");

                    if (result == null) {

                        html.append("<td colspan='5' class='empty'>Нет данных</td>");

                    } else {

                        html.append(createStatus(result.isCompiled()));

                        html.append(createStatus(result.isJavadocGenerated()));

                        html.append(createStatus(result.isStylePassed()));

                        boolean softPassed = false;
                        boolean hardPassed = false;

                        if (submission != null
                                && submission.getSubmitDate() != null) {

                            if (task.getSoftDeadline() != null) {

                                softPassed =
                                        !submission.getSubmitDate()
                                                .isAfter(task.getSoftDeadline());
                            }

                            if (task.getHardDeadline() != null) {

                                hardPassed =
                                        !submission.getSubmitDate()
                                                .isAfter(task.getHardDeadline());
                            }
                        }

                        html.append(createDeadlineStatus(softPassed));

                        html.append(createDeadlineStatus(hardPassed));

                        html.append("<td class='tests'>");

                        html.append("<span class='passed-tests'>")
                                .append(result.getTestsPassed())
                                .append("</span>");

                        html.append(" / ");

                        html.append("<span class='failed-tests'>")
                                .append(result.getTestsFailed())
                                .append("</span>");

                        html.append("</td>");

                        html.append("<td class='score'>")
                                .append(result.getFinalScore())
                                .append("</td>");
                    }

                    html.append("</tr>");
                }
            }

            html.append("</table>");
            html.append("</div>");
        }

        
        html.append("<div class='summary-card'>");

        html.append("<div class='summary-title'>Общая статистика</div>");

        html.append("<table>");

        html.append("<tr>");
        html.append("<th>Студент</th>");

        for (Task task : config.getTasks()) {

            html.append("<th>")
                    .append(task.getId())
                    .append("</th>");
        }

        html.append("<th>Итого</th>");
        html.append("<th>%</th>");
        html.append("</tr>");

        for (Group group : config.getGroups()) {

            for (Student student : group.getStudents()) {

                double total = 0;
                double max = 0;

                html.append("<tr>");

                html.append("<td class='student-name'>")
                        .append(student.getFullName())
                        .append("</td>");

                for (Task task : config.getTasks()) {

                    max += task.getMaxScore();

                    Submission submission =
                            findSubmission(config,
                                    student,
                                    task);

                    if (submission == null
                            || !results.containsKey(submission)) {

                        html.append("<td class='empty'>0</td>");

                        continue;
                    }

                    SubmissionResult result =
                            results.get(submission);

                    double score =
                            result.getFinalScore();

                    total += score;

                    html.append("<td>")
                            .append(score)
                            .append("</td>");
                }

                int percent =
                        max == 0
                                ? 0
                                : (int)((total / max) * 100);

                html.append("<td class='score'>")
                        .append(total)
                        .append("</td>");

                html.append("<td class='percent'>")
                        .append(percent)
                        .append("%</td>");

                html.append("</tr>");
            }
        }

        html.append("</table>");
        html.append("</div>");

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        try (FileWriter writer =
                     new FileWriter(outputFile)) {

            writer.write(html.toString());

        } catch (Exception e) {

            System.out.println(
                    "Ошибка генерации HTML"
            );
        }
    }

    private String createDeadlineStatus(boolean passed) {

        if (passed) {

            return """
                <td>
                    <span class='deadline-ok'>
                        Passed
                    </span>
                </td>
                """;
        }

        return """
            <td>
                <span class='deadline-fail'>
                    Missed
                </span>
            </td>
            """;
    }

    private String createStatus(boolean ok) {

        if (ok) {

            return "<td class='status-ok'>●</td>";
        }

        return "<td class='status-fail'>●</td>";
    }

    private Submission findSubmission(CourseConfig config,
                                      Student student,
                                      Task task) {

        for (Submission submission :
                config.getSubmissions()) {

            boolean sameStudent =
                    submission.getStudentId()
                            .equals(student.getGithubId());

            boolean sameTask =
                    submission.getTaskId()
                            .equals(task.getId());

            if (sameStudent && sameTask) {

                return submission;
            }
        }

        return null;
    }
}