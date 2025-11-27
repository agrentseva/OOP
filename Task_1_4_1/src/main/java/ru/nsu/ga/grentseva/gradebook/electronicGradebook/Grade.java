package ru.nsu.ga.grentseva.gradebook.electronicGradebook;

public class Grade {
    private final String subject;
    private final GradeType type;
    private final int value;
    private final int semester;

    public Grade(String subject, GradeType type, int value, int semester) {
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException("Номер семестра должен быть от 1 до 8.");
        }

        switch (type) {
            case CREDIT -> {
                if (value != 2 && value != 5) {
                    throw new IllegalArgumentException("Для зачета значение оценки должно быть 5 (зачтено) или 2 (не зачтено).");
                }
            }
            case EXAM, DIFF_CREDIT, PRACTICE_REPORT, THESIS -> {
                if (value < 2 || value > 5) {
                    throw new IllegalArgumentException("Для экзамена, дифф. зачета, отчета или ВКР значение оценки должно быть от 2 до 5.");
                }
            }
        }

        this.subject = subject;
        this.type = type;
        this.value = value;
        this.semester = semester;
    }

    public GradeType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }
}
