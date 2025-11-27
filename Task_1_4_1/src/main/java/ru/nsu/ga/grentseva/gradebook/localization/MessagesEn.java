package ru.nsu.ga.grentseva.gradebook.localization;

import ru.nsu.ga.grentseva.gradebook.electronicGradebook.GradeType;

public class MessagesEn implements Messages {

    @Override
    public String gradebookEmpty() {
        return "Gradebook is empty.";
    }

    @Override
    public String studentForm() {
        return "Student form";
    }

    @Override
    public String budget() {
        return "Budget";
    }

    @Override
    public String paid() {
        return "Paid";
    }

    @Override
    public String semester() {
        return "Semester";
    }

    @Override
    public String passed() {
        return "Passed";
    }

    @Override
    public String notPassed() {
        return "Not Passed";
    }

    @Override
    public String gradeType(GradeType type) {
        return switch (type) {
            case EXAM -> "Exam";
            case CREDIT -> "Credit";
            case DIFF_CREDIT -> "Differentiated Credit";
            case PRACTICE_REPORT -> "Practice Report";
            case THESIS -> "Thesis";
        };
    }

    @Override
    public String averageGrade() {
        return "Average grade";
    }

    @Override
    public String canTransferToBudget() {
        return "Can transfer to budget";
    }

    @Override
    public String redDiplomaForecast() {
        return "Red diploma forecast";
    }

    @Override
    public String canGetIncreasedScholarship() {
        return "Increased scholarship this semester";
    }

    @Override
    public String yes() {
        return "Yes";
    }

    @Override
    public String no() {
        return "No";
    }

    @Override
    public String redDiplomaImpossibleUnpassedCredit() {
        return "Red diploma impossible: there are unpassed credits.";
    }

    @Override
    public String redDiplomaImpossibleThesis() {
        return "Red diploma impossible: Thesis must be graded 5.";
    }

    @Override
    public String redDiplomaImpossibleSatisfactory() {
        return "Red diploma impossible: there is a satisfactory grade.";
    }

    @Override
    public String redDiplomaNoFinalGrades() {
        return "No final grades for diploma calculation.";
    }

    @Override
    public String redDiplomaNotEnoughFives(double percent) {
        return String.format("Red diploma impossible: not enough fives. Currently %.2f%%, minimum required 75%%.", percent);
    }

    @Override
    public String redDiplomaPossible() {
        return "Red diploma possible: all requirements met!";
    }

    @Override
    public String redDiplomaForecastFewGrades() {
        return "Not enough grades yet for forecast, chance remains if student continues with excellent performance.";
    }

    @Override
    public String redDiplomaForecastImpossible(int maxFives, int requiredFives) {
        return String.format("Even with perfect grades, chance lost: max fives by graduation — %d, minimum required %d.", maxFives, requiredFives);
    }

    @Override
    public String redDiplomaForecastPossible(int currentFives, int totalFinal, double percent, int requiredFives) {
        return String.format("Chance for red diploma exists. Currently fives: %d of %d (%.2f%%). Minimum required by graduation: %d fives.", currentFives, totalFinal, percent, requiredFives);
    }
}
