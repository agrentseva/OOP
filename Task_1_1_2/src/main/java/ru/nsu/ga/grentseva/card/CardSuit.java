package ru.nsu.ga.grentseva.card;

public enum CardSuit {
    HEARTS("Червы", "Hearts", "Червонный", "Червонная"),
    DIAMONDS("Бубен", "Diamonds", "Бубновый", "Бубновая"),
    CLUBS("Трефы", "Clubs", "Трефовый", "Трефовая"),
    SPADES("Пики", "Spades", "Пиковый", "Пиковая");

    private final String ruNoun;
    private final String enNoun;
    private final String adjMale;
    private final String adjFemale;

    CardSuit(String ruNoun, String enNoun, String adjMale, String adjFemale) {
        this.ruNoun = ruNoun;
        this.enNoun = enNoun;
        this.adjMale = adjMale;
        this.adjFemale = adjFemale;
    }

    public String getNoun(boolean english) {
        return english ? enNoun : ruNoun;
    }

    public String getAdjMale() {
        return adjMale;
    }

    public String getAdjFemale() {
        return adjFemale;
    }

    @Override
    public String toString() {
        return ruNoun;
    }
}
