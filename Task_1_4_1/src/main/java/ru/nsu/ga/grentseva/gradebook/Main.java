package ru.nsu.ga.grentseva.gradebook;

import ru.nsu.ga.grentseva.gradebook.electronicGradebook.Grade;
import ru.nsu.ga.grentseva.gradebook.electronicGradebook.GradeType;
import ru.nsu.ga.grentseva.gradebook.electronicGradebook.Gradebook;
import ru.nsu.ga.grentseva.gradebook.localization.Messages;
import ru.nsu.ga.grentseva.gradebook.localization.MessagesRu;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Messages messagesRu = new MessagesRu();

        System.out.println("=== Русская версия ===");
        Gradebook gradebookRu = new Gradebook(true, messagesRu);

        gradebookRu.addGrade(new Grade("Математика", GradeType.EXAM, 5, 1));
        gradebookRu.addGrade(new Grade("Физика", GradeType.EXAM, 4, 1));
        gradebookRu.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, 1));
        gradebookRu.addGrade(new Grade("Отчет по практике", GradeType.PRACTICE_REPORT, 5, 1));

        gradebookRu.addGrade(new Grade("Математика", GradeType.EXAM, 5, 2));
        gradebookRu.addGrade(new Grade("Информатика", GradeType.EXAM, 5, 2));
        gradebookRu.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, 2));
        gradebookRu.addGrade(new Grade("Отчет по практике", GradeType.PRACTICE_REPORT, 5, 2));

        gradebookRu.addGrade(new Grade("Математика", GradeType.EXAM, 4, 3));
        gradebookRu.addGrade(new Grade("Химия", GradeType.EXAM, 5, 3));
        gradebookRu.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, 3));

        gradebookRu.addGrade(new Grade("Математика", GradeType.EXAM, 5, 4));
        gradebookRu.addGrade(new Grade("Физика", GradeType.EXAM, 5, 4));
        gradebookRu.addGrade(new Grade("Отчет по практике", GradeType.PRACTICE_REPORT, 5, 4));

        gradebookRu.addGrade(new Grade("Информатика", GradeType.EXAM, 5, 5));
        gradebookRu.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, 5));

        gradebookRu.addGrade(new Grade("Математика", GradeType.EXAM, 5, 6));
        gradebookRu.addGrade(new Grade("Химия", GradeType.EXAM, 5, 6));

        gradebookRu.addGrade(new Grade("Физика", GradeType.EXAM, 5, 7));
        gradebookRu.addGrade(new Grade("Информатика", GradeType.EXAM, 5, 7));

        gradebookRu.addGrade(new Grade("ВКР", GradeType.THESIS, 5, 8));

        gradebookRu.printGradebook();
    }
}

