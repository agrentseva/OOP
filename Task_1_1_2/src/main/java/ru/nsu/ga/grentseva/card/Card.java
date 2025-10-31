package ru.nsu.ga.grentseva.card;

public class Card {
    private final CardRank rank;
    private final CardSuit suit;

    public Card(CardRank rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public CardRank getRank() {
        return rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public String getCardName(boolean english) {
        if (english) {
            return rank.getNoun(true) + " of " + suit.getNoun(true) + " (" + rank.getValue() + ")";
        } else {
            switch (rank) {
                case JACK:
                case KING:
                    return suit.getAdjMale() + " " + rank.getNoun(false) + " (" + rank.getValue() + ")";
                case QUEEN:
                    return suit.getAdjFemale() + " " + rank.getNoun(false) + " (" + rank.getValue() + ")";
                default:
                    return rank.getNoun(false) + " " + suit.getNoun(false) + " (" + rank.getValue() + ")";
            }
        }
    }

    public String toString() {
        return getCardName(false);
    }

    public String toString(boolean english) {
        return getCardName(english);
    }

    public int getCardValue() {
        return rank.getValue();
    }
}
