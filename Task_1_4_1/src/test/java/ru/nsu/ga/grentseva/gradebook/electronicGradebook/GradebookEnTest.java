package ru.nsu.ga.grentseva.gradebook.electronicGradebook;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.gradebook.localization.Messages;
import ru.nsu.ga.grentseva.gradebook.localization.MessagesEn;

class GradebookEnTest {
    private Messages messages;
    private Gradebook gradebook;

    @BeforeEach
    void setUp() {
        messages = new MessagesEn();
        gradebook = new Gradebook(true, messages);
    }

    @Test
    void testMessagesNotNull() {
        assertNotNull(messages.gradebookEmpty());
        assertNotNull(messages.studentForm());
        assertNotNull(messages.budget());
        assertNotNull(messages.paid());
        assertNotNull(messages.semester());
        assertNotNull(messages.passed());
        assertNotNull(messages.notPassed());
        assertNotNull(messages.averageGrade());
        assertNotNull(messages.canTransferToBudget());
        assertNotNull(messages.redDiplomaForecast());
        assertNotNull(messages.redDiplomaImpossibleUnpassedCredit());
        assertNotNull(messages.redDiplomaImpossibleSatisfactory());
        assertNotNull(messages.redDiplomaImpossibleThesis());
        assertNotNull(messages.redDiplomaNoFinalGrades());
        assertNotNull(messages.redDiplomaNotEnoughFives(80.0));
        assertNotNull(messages.redDiplomaPossible());
        assertNotNull(messages.redDiplomaForecastFewGrades());
        assertNotNull(messages.redDiplomaForecastImpossible(5, 6));
        assertNotNull(messages.redDiplomaForecastPossible(5, 6, 80.0, 6));
        assertNotNull(messages.canGetIncreasedScholarship());
        assertNotNull(messages.yes());
        assertNotNull(messages.no());
    }

    @Test
    void testPrintGradebookEnglish() {
        gradebook.addGrade(new Grade("Mathematics", GradeType.EXAM, 5, 1));
        gradebook.addGrade(new Grade("Physics", GradeType.DIFF_CREDIT, 5, 1));
        gradebook.addGrade(new Grade("Physical Education", GradeType.CREDIT, 5, 1));
        gradebook.addGrade(new Grade("Practice Report", GradeType.PRACTICE_REPORT, 5, 1));

        gradebook.printGradebook();
    }

    @Test
    void testRedDiplomaWithFives() {
        for (int sem = 1; sem <= 8; sem++) {
            gradebook.addGrade(new Grade("Math", GradeType.EXAM, 5, sem));
            gradebook.addGrade(new Grade("Physics", GradeType.DIFF_CREDIT, 5, sem));
            gradebook.addGrade(new Grade("PE", GradeType.CREDIT, 5, sem));
        }
        gradebook.addGrade(new Grade("Thesis", GradeType.THESIS, 5, 8));

        assertTrue(gradebook.canGetRedDiplomaWithMessage());
    }

    @Test
    void testCannotGetScholarshipAfterThesis() {
        gradebook.addGrade(new Grade("Thesis", GradeType.THESIS, 5, 8));
        assertFalse(gradebook.canGetIncreasedScholarship());
    }

    @Test
    void testAverageGradeEnglish() {
        gradebook.addGrade(new Grade("Math", GradeType.EXAM, 5, 1));
        gradebook.addGrade(new Grade("Physics", GradeType.DIFF_CREDIT, 4, 1));
        gradebook.addGrade(new Grade("PE", GradeType.CREDIT, 5, 1));

        assertEquals(4.5, gradebook.getAverageGrade());
    }
}
