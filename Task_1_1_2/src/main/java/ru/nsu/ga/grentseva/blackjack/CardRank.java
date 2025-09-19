package ru.nsu.ga.grentseva.blackjack;

enum CardRank {
    TWO(2, "Двойка"),
    THREE(3, "Тройка"),
    FOUR(4, "Четверка"),
    FIVE(5, "Пятерка"),
    SIX(6, "Шестерка"),
    SEVEN(7, "Семерка"),
    EIGHT(8, "Восьмерка"),
    NINE(9, "Девятка"),
    TEN(10, "Десятка"),
    JACK(10, "валет"),
    QUEEN(10, "дама"),
    KING(10, "король"),
    ACE(11, "Туз");

    private final int value;
    private final String noun;

    CardRank(int value, String noun) {
        this.value = value;
        this.noun = noun;
    }

    public int getValue() {
        return value;
    }

    public String getNoun() {
        return noun;
    }
}
