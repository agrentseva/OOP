package ru.nsu.ga.grentseva.gradebook.localization;

import ru.nsu.ga.grentseva.gradebook.electronicGradebook.GradeType;

public class MessagesRu implements Messages {

    @Override
    public String gradebookEmpty() {
        return "Зачетная книжка пуста.";
    }

    @Override
    public String studentForm() {
        return "Форма обучения";
    }

    @Override
    public String budget() {
        return "Бюджет";
    }

    @Override
    public String paid() {
        return "Платная";
    }

    @Override
    public String semester() {
        return "Семестр";
    }

    @Override
    public String passed() {
        return "Зачтено";
    }

    @Override
    public String notPassed() {
        return "Не зачтено";
    }

    @Override
    public String gradeType(GradeType type) {
        return switch (type) {
            case EXAM -> "Экзамен";
            case CREDIT -> "Зачет";
            case DIFF_CREDIT -> "Дифференцированный зачет";
            case PRACTICE_REPORT -> "Отчет по практике";
            case THESIS -> "ВКР";
        };
    }

    @Override
    public String averageGrade() {
        return "Средний балл";
    }

    @Override
    public String canTransferToBudget() {
        return "Возможность перевода на бюджет";
    }

    @Override
    public String redDiplomaForecast() {
        return "Прогноз красного диплома";
    }

    @Override
    public String canGetIncreasedScholarship() {
        return "Повышенная стипендия в текущем семестре";
    }

    @Override
    public String yes() {
        return "Да";
    }

    @Override
    public String no() {
        return "Нет";
    }

    @Override
    public String redDiplomaImpossibleUnpassedCredit() {
        return "Красный диплом невозможен: есть незачтённые зачёты.";
    }

    @Override
    public String redDiplomaImpossibleThesis() {
        return "Красный диплом невозможен: ВКР должна быть оценена на 5.";
    }

    @Override
    public String redDiplomaImpossibleSatisfactory() {
        return "Красный диплом невозможен: есть оценка 'удовлетворительно'.";
    }

    @Override
    public String redDiplomaNoFinalGrades() {
        return "Нет финальных оценок для расчёта диплома.";
    }

    @Override
    public String redDiplomaNotEnoughFives(double percent) {
        return String.format("Красный диплом невозможен: недостаточно пятёрок. Сейчас %.2f%%, требуется минимум 75%%.", percent);
    }

    @Override
    public String redDiplomaPossible() {
        return "Красный диплом возможен: все требования выполнены!";
    }

    @Override
    public String redDiplomaForecastFewGrades() {
        return "Оценок пока мало для прогноза, шанс получить красный диплом сохраняется при дальнейшем отличном обучении.";
    }

    @Override
    public String redDiplomaForecastImpossible(int maxFives, int requiredFives) {
        return String.format("Даже при идеальных оценках шанс потерян: максимум пятёрок к окончанию — %d, требуется минимум %d.", maxFives, requiredFives);
    }

    @Override
    public String redDiplomaForecastPossible(int currentFives, int totalFinal, double percent, int requiredFives) {
        return String.format("Шанс получить красный диплом есть. Сейчас пятёрок: %d из %d (%.2f%%). К окончанию потребуется минимум %d пятёрок.", currentFives, totalFinal, percent, requiredFives);
    }
}
