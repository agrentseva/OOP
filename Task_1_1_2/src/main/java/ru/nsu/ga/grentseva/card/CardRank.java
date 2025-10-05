package ru.nsu.ga.grentseva.card;

public enum CardRank {
    TWO(2, "Двойка", "Two"),
    THREE(3, "Тройка", "Three"),
    FOUR(4, "Четверка", "Four"),
    FIVE(5, "Пятерка", "Five"),
    SIX(6, "Шестерка", "Six"),
    SEVEN(7, "Семерка", "Seven"),
    EIGHT(8, "Восьмерка", "Eight"),
    NINE(9, "Девятка", "Nine"),
    TEN(10, "Десятка", "Ten"),
    JACK(10, "Валет", "Jack"),
    QUEEN(10, "Дама", "Queen"),
    KING(10, "Король", "King"),
    ACE(11, "Туз", "Ace");

    private final int value;
    private final String ru;
    private final String en;

    CardRank(int value, String ru, String en) {
        this.value = value;
        this.ru = ru;
        this.en = en;
    }

    public int getValue() {
        return value;
    }

    public String getNoun(boolean english) {
        return english ? en : ru;
    }

    @Override
    public String toString() {
        return ru; // по умолчанию русский
    }

    public String toString(boolean english) {
        return english ? en : ru;
    }
}
