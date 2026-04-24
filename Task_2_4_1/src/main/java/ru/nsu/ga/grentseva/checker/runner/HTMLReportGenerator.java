package ru.nsu.ga.grentseva.checker.runner;

import ru.nsu.ga.grentseva.checker.model.*;

import java.io.FileWriter;
import java.util.Map;

public class HTMLReportGenerator {

    public void generate(Config config,
                         Map<Submission, SubmissionResult> results,
                         String path) {

        StringBuilder sb = new StringBuilder();

        sb.append("<html><head><meta charset='UTF-8'>");
        sb.append("<style>");
        sb.append("table{border-collapse:collapse;margin:20px;}");
        sb.append("td,th{border:1px solid black;padding:6px;text-align:center;}");
        sb.append(".ok{color:green;font-weight:bold;}");
        sb.append(".fail{color:red;font-weight:bold;}");
        sb.append("</style></head><body>");

        sb.append("<h1>Отчет</h1>");

        for (Task task : config.getTasks()) {

            sb.append("<h2>Лабораторная ")
                    .append(task.getId())
                    .append(" (").append(task.getName()).append(")</h2>");

            sb.append("<table>");
            sb.append("<tr>")
                    .append("<th>Студент</th>")
                    .append("<th>Сборка</th>")
                    .append("<th>Документация</th>")
                    .append("<th>Style</th>")
                    .append("<th>Тесты</th>")
                    .append("<th>Бонус</th>")
                    .append("<th>Балл</th>")
                    .append("</tr>");

            for (Group group : config.getGroups()) {
                for (Student student : group.getStudents()) {

                    Submission sub = findSubmission(config, student, task);
                    SubmissionResult r = sub != null ? results.get(sub) : null;

                    sb.append("<tr>");
                    sb.append("<td>").append(student.getFullName()).append("</td>");

                    if (r != null) {

                        sb.append(check(r.isCompileSuccess()));
                        sb.append(check(r.isCompileSuccess()));
                        sb.append(check(r.isCompileSuccess()));

                        sb.append("<td>")
                                .append(r.getTestsPassed()).append("/")
                                .append(r.getTestsFailed()).append("/")
                                .append(r.getTestsSkipped())
                                .append("</td>");

                        sb.append("<td>").append(r.getBonus()).append("</td>");
                        sb.append("<td><b>").append(r.getFinalScore()).append("</b></td>");

                    } else {
                        sb.append("<td colspan='6'>0</td>");
                    }

                    sb.append("</tr>");
                }
            }

            sb.append("</table>");
        }

        sb.append("<h2>Общая статистика</h2>");
        sb.append("<table>");

        sb.append("<tr><th>Студент</th>");
        for (Task t : config.getTasks()) {
            sb.append("<th>").append(t.getId()).append("</th>");
        }
        sb.append("<th>Сумма</th><th>%</th></tr>");

        for (Group group : config.getGroups()) {
            for (Student student : group.getStudents()) {

                double sum = 0;
                double max = 0;

                sb.append("<tr>");
                sb.append("<td>").append(student.getFullName()).append("</td>");

                for (Task task : config.getTasks()) {

                    max += task.getMaxScore();

                    Submission sub = findSubmission(config, student, task);

                    if (sub != null && results.containsKey(sub)) {
                        double s = results.get(sub).getFinalScore();
                        sum += s;
                        sb.append("<td>").append(s).append("</td>");
                    } else {
                        sb.append("<td>0</td>");
                    }
                }

                int percent = max > 0 ? (int)((sum / max) * 100) : 0;

                sb.append("<td><b>").append(sum).append("</b></td>");
                sb.append("<td>").append(percent).append("%</td>");
                sb.append("</tr>");
            }
        }

        sb.append("</table>");
        sb.append("</body></html>");

        try (FileWriter w = new FileWriter(path)) {
            w.write(sb.toString());
        } catch (Exception ignored) {}
    }

    private String check(boolean ok) {
        return "<td class='" + (ok ? "ok" : "fail") + "'>"
                + (ok ? "+" : "-") + "</td>";
    }

    private Submission findSubmission(Config config, Student s, Task t) {
        return config.getSubmissions().stream()
                .filter(x -> x.getStudentId().equals(s.getGithubId())
                        && x.getTaskId().equals(t.getId()))
                .findFirst()
                .orElse(null);
    }
}