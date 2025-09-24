package ru.nsu.ga.grentseva.blackjack;

enum CardSuit {
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
