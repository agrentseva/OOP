package ru.nsu.ga.grentseva.gradebook.localization;

import ru.nsu.ga.grentseva.gradebook.electronicGradebook.GradeType;

public interface Messages {
    String gradebookEmpty();
    String studentForm();
    String budget();
    String paid();
    String semester();
    String passed();
    String notPassed();
    String gradeType(GradeType type);
    String averageGrade();
    String canTransferToBudget();
    String redDiplomaForecast();
    String canGetIncreasedScholarship();
    String yes();
    String no();
    String redDiplomaImpossibleUnpassedCredit();
    String redDiplomaImpossibleThesis();
    String redDiplomaImpossibleSatisfactory();
    String redDiplomaNoFinalGrades();
    String redDiplomaNotEnoughFives(double percent);
    String redDiplomaPossible();
    String redDiplomaForecastFewGrades();
    String redDiplomaForecastImpossible(int maxFives, int requiredFives);
    String redDiplomaForecastPossible(int currentFives, int totalFinal, double percent, int requiredFives);
}
