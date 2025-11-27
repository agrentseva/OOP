package ru.nsu.ga.grentseva.gradebook.electronicGradebook;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.gradebook.localization.Messages;
import ru.nsu.ga.grentseva.gradebook.localization.MessagesRu;

class GradebookRuTest {
    private Messages messages;
    private Gradebook gradebook;

    @BeforeEach
    void setUp() {
        messages = new MessagesRu();
        gradebook = new Gradebook(true, messages); // бюджетник
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
    void testPrintGradebookRussian() {
        gradebook.addGrade(new Grade("Математика", GradeType.EXAM, 5, 1));
        gradebook.addGrade(new Grade("Физика", GradeType.DIFF_CREDIT, 5, 1));
        gradebook.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, 1));
        gradebook.addGrade(new Grade("Отчет по практике", GradeType.PRACTICE_REPORT, 5, 1));

        gradebook.printGradebook();
    }

    @Test
    void testRedDiplomaWithFives() {
        for (int sem = 1; sem <= 8; sem++) {
            gradebook.addGrade(new Grade("Математика", GradeType.EXAM, 5, sem));
            gradebook.addGrade(new Grade("Физика", GradeType.DIFF_CREDIT, 5, sem));
            gradebook.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, sem));
        }
        gradebook.addGrade(new Grade("ВКР", GradeType.THESIS, 5, 8));

        assertTrue(gradebook.canGetRedDiplomaWithMessage());
    }

    @Test
    void testCannotGetScholarshipAfterThesis() {
        gradebook.addGrade(new Grade("ВКР", GradeType.THESIS, 5, 8));
        assertFalse(gradebook.canGetIncreasedScholarship());
    }

    @Test
    void testAverageGradeRussian() {
        gradebook.addGrade(new Grade("Математика", GradeType.EXAM, 5, 1));
        gradebook.addGrade(new Grade("Физика", GradeType.DIFF_CREDIT, 4, 1));
        gradebook.addGrade(new Grade("Физкультура", GradeType.CREDIT, 5, 1));

        assertEquals(4.5, gradebook.getAverageGrade());
    }
}
