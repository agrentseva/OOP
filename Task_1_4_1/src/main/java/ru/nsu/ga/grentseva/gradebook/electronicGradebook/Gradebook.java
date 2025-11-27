package ru.nsu.ga.grentseva.gradebook.electronicGradebook;

import ru.nsu.ga.grentseva.gradebook.localization.Messages;

import java.util.ArrayList;
import java.util.List;

public class Gradebook {
    private final List<Grade> grades = new ArrayList<>();
    private boolean isBudget;
    private final Messages messages;

    public Gradebook(boolean isBudget, Messages messages) {
        this.isBudget = isBudget;
        this.messages = messages;
    }

    public void addGrade(Grade g) {
        grades.add(g);
    }

    public boolean isBudget() {
        return isBudget;
    }

    public double getAverageGrade() {
        int sum = 0;
        int count = 0;

        for (Grade g : grades) {
            if (g.getType() == GradeType.CREDIT) {
                continue;
            }
            sum += g.getValue();
            count++;
        }
        return (count == 0) ? 0.0 : (double) sum / count;
    }

    public boolean canTransferToBudget() {
        if (isBudget || grades.isEmpty()) return false;

        boolean hasThesis = grades.stream().anyMatch(g -> g.getType() == GradeType.THESIS);
        if (hasThesis) return false;

        int maxSem = grades.stream().mapToInt(Grade::getSemester).max().orElse(1);
        int last = maxSem;
        int prev = maxSem - 1;

        for (Grade g : grades) {
            if ((g.getSemester() == last || g.getSemester() == prev)
                    && g.getType() == GradeType.EXAM && g.getValue() <= 3) {
                return false;
            }
        }
        return true;
    }

    public boolean canGetRedDiplomaWithMessage() {
        if (grades.isEmpty()) return false;

        int maxSem = grades.stream().mapToInt(Grade::getSemester).max().orElse(1);

        boolean hasSatisfactory = false;
        boolean hasUnpassedCredit = false;
        int totalFinal = 0;
        int excellent = 0;
        boolean thesisExcellent = false;

        for (Grade g : grades) {
            if (g.getType() == GradeType.CREDIT) {
                if (g.getValue() < 5) hasUnpassedCredit = true;
                continue;
            }

            int v = g.getValue();
            totalFinal++;
            if (v == 5) excellent++;
            if (v == 3) hasSatisfactory = true;
            if (g.getType() == GradeType.THESIS && v == 5) thesisExcellent = true;
        }

        if (hasUnpassedCredit) {
            System.out.println(messages.redDiplomaImpossibleUnpassedCredit());
            return false;
        }

        if (maxSem >= 8) {
            if (!thesisExcellent) {
                System.out.println(messages.redDiplomaImpossibleThesis());
                return false;
            }
            if (hasSatisfactory) {
                System.out.println(messages.redDiplomaImpossibleSatisfactory());
                return false;
            }
            if (totalFinal == 0) {
                System.out.println(messages.redDiplomaNoFinalGrades());
                return false;
            }
            double ratio = (double) excellent / totalFinal;
            if (ratio < 0.75) {
                System.out.println(messages.redDiplomaNotEnoughFives(ratio * 100));
                return false;
            }
            System.out.println(messages.redDiplomaPossible());
            return true;
        }

        if (hasSatisfactory) {
            System.out.println(messages.redDiplomaImpossibleSatisfactory());
            return false;
        }
        if (totalFinal == 0) {
            System.out.println(messages.redDiplomaForecastFewGrades());
            return true;
        }

        int estimatedTotal = (int) Math.round((double) totalFinal / maxSem * 8);
        int remaining = estimatedTotal - totalFinal;
        int requiredFives = (int) Math.ceil(estimatedTotal * 0.75);
        int maxPossibleFives = excellent + remaining;

        if (maxPossibleFives < requiredFives) {
            System.out.println(messages.redDiplomaForecastImpossible(maxPossibleFives, requiredFives));
            return false;
        }

        System.out.println(messages.redDiplomaForecastPossible(
                excellent, totalFinal, (double) excellent / totalFinal * 100, requiredFives));
        return true;
    }

    public boolean canGetIncreasedScholarship() {
        if (!isBudget) return false;

        boolean hasThesis = grades.stream().anyMatch(g -> g.getType() == GradeType.THESIS);
        if (hasThesis) return false;

        int maxSem = grades.stream()
                .mapToInt(Grade::getSemester)
                .max()
                .orElse(1);

        for (Grade g : grades) {
            if (g.getSemester() != maxSem) continue;

            if (g.getValue() != 5) return false;
        }

        return true;
    }

    public void printGradebook() {
        if (grades.isEmpty()) {
            System.out.println(messages.gradebookEmpty());
            return;
        }

        System.out.println("==================================================");
        System.out.printf("%s: %s%n", messages.studentForm(), isBudget ? messages.budget() : messages.paid());
        System.out.println("--------------------------------------------------");

        for (int sem = 1; sem <= 8; sem++) {
            int finalSem = sem;
            List<Grade> semGrades = grades.stream()
                    .filter(g -> g.getSemester() == finalSem)
                    .toList();

            if (semGrades.isEmpty()) continue;

            System.out.printf("%s %d:%n", messages.semester(), sem);
            for (Grade g : semGrades) {
                String gradeStr;
                if (g.getType() == GradeType.CREDIT) {
                    gradeStr = (g.getValue() == 5) ? messages.passed() : messages.notPassed();
                } else {
                    gradeStr = String.valueOf(g.getValue());
                }
                System.out.printf("  %-20s %-18s %s%n",
                        g.getSubject(),
                        messages.gradeType(g.getType()),
                        gradeStr);
            }
            System.out.println("--------------------------------------------------");
        }

        System.out.printf("%s: %.2f%n", messages.averageGrade(), getAverageGrade());

        boolean hasThesis = grades.stream().anyMatch(g -> g.getType() == GradeType.THESIS);

        if (!isBudget && !hasThesis) {
            System.out.printf("%s: %s%n", messages.canTransferToBudget(), canTransferToBudget() ? messages.yes() : messages.no());
        }

        System.out.print(messages.redDiplomaForecast() + ": ");
        canGetRedDiplomaWithMessage();

        if (!hasThesis) {
            System.out.printf("%s: %s%n", messages.canGetIncreasedScholarship(), canGetIncreasedScholarship() ? messages.yes() : messages.no());
        }

        System.out.println("==================================================");
    }
}
