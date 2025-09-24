package ru.nsu.ga.grentseva.blackjack;

/**
 * Класс карты.
 * Содержит ранг и масть карты.
 */
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
                return suit.getAdjMale() + " " + rank.getNoun() + " (" + rank.getValue() + ")";
            case QUEEN:
                return suit.getAdjFemale() + " " + rank.getNoun() + " (" + rank.getValue() + ")";
            default:
                return rank.getNoun() + " " + suit.getNoun() + " (" + rank.getValue() + ")";
        }
    }


    @Override
    public String toString() {
        return getCardName();
    }

    public int getCardValue() {
        return rank.getValue();
    }
}

