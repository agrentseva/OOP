package ru.nsu.ga.grentseva.Task_1_1_2;

public class Card {
    private final CardRank rank;
    private final CardSuit suit;

    public Card(CardRank rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getCardName() {
        switch (rank) {
            case JACK:
            case KING:
                return suit.getAdjMale() + " " + rank.getNoun() + " (" + rank.getValue() + ")" ;
            case QUEEN:
                return suit.getAdjFemale() + " " + rank.getNoun() + " (" + rank.getValue() + ")" ;
            default:
                return rank.getNoun() + " " + suit.getNoun() + " (" + rank.getValue() + ")" ;
        }
    }

    @Override
    public String toString() { return getCardName(); }

    public int getCardValue() { return rank.getValue(); }
}

enum CardSuit{
    HEARTS("Червы", "Червонный", "Червонная"),
    DIAMONDS("Бубен", "Бубновый", "Бубновая"),
    CLUBS("Трефы", "Трефовый", "Трефовая"),
    SPADES("Пики", "Пиковый", "Пиковая");

    private final String noun;
    private final String adjMale;
    private final String adjFemale;

    CardSuit(String noun, String adjMale, String adjFemale) {
        this.noun = noun;
        this.adjMale = adjMale;
        this.adjFemale = adjFemale;
    }

    public String getNoun() {
        return noun;
    }

    public String getAdjMale() {
        return adjMale;
    }

    public String getAdjFemale() {
        return adjFemale;
    }
}

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