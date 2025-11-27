package ru.nsu.ga.grentseva.gradebook.electronicGradebook;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.gradebook.localization.Messages;
import ru.nsu.ga.grentseva.gradebook.localization.MessagesRu;

class GradebookTest {
    private Messages messages;
    private Gradebook gradebook;

    @BeforeEach
    void setUp() {
        messages = new MessagesRu();
        gradebook = new Gradebook(false, messages);
    }

    @Test
    void testAverageGrade() {
        gradebook.addGrade(new Grade("Math", GradeType.EXAM, 5, 1));
        gradebook.addGrade(new Grade("Physics", GradeType.EXAM, 4, 1));
        gradebook.addGrade(new Grade("PE", GradeType.CREDIT, 5, 1));

        assertEquals(4.5, gradebook.getAverageGrade());
    }

    @Test
    void testCannotTransferAfterThesis() {
        gradebook.addGrade(new Grade("Thesis", GradeType.THESIS, 5, 8));
        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudgetWithoutFailures() {
        gradebook.addGrade(new Grade("Math", GradeType.EXAM, 5, 7));
        gradebook.addGrade(new Grade("Physics", GradeType.EXAM, 4, 8));
        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testCannotTransferToBudgetWithLowExam() {
        gradebook.addGrade(new Grade("Math", GradeType.EXAM, 3, 8));
        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testRedDiplomaPossible() {
        for (int sem = 1; sem <= 8; sem++) {
            gradebook.addGrade(new Grade("Math", GradeType.EXAM, 5, sem));
            gradebook.addGrade(new Grade("Physics", GradeType.DIFF_CREDIT, 5, sem));
            gradebook.addGrade(new Grade("PE", GradeType.CREDIT, 5, sem));
        }
        gradebook.addGrade(new Grade("Thesis", GradeType.THESIS, 5, 8));

        assertTrue(gradebook.canGetRedDiplomaWithMessage());
    }

    @Test
    void testRedDiplomaImpossibleDueToSatisfactory() {
        gradebook.addGrade(new Grade("Math", GradeType.EXAM, 3, 1));
        assertFalse(gradebook.canGetRedDiplomaWithMessage());
    }

    @Test
    void testRedDiplomaImpossibleDueToUnpassedCredit() {
        gradebook.addGrade(new Grade("PE", GradeType.CREDIT, 2, 1));
        assertFalse(gradebook.canGetRedDiplomaWithMessage());
    }

    @Test
    void testIncreasedScholarship() {
        gradebook = new Gradebook(true, messages); // бюджетник
        gradebook.addGrade(new Grade("Math", GradeType.EXAM, 5, 1));
        gradebook.addGrade(new Grade("Physics", GradeType.DIFF_CREDIT, 5, 1));
        gradebook.addGrade(new Grade("PE", GradeType.CREDIT, 5, 1));

        assertTrue(gradebook.canGetIncreasedScholarship());
    }

    @Test
    void testNoScholarshipAfterThesis() {
        gradebook = new Gradebook(true, messages);
        gradebook.addGrade(new Grade("Thesis", GradeType.THESIS, 5, 8));
        assertFalse(gradebook.canGetIncreasedScholarship());
    }

    @Test
    void testPrintGradebookEmpty() {
        gradebook.printGradebook();
    }
}
