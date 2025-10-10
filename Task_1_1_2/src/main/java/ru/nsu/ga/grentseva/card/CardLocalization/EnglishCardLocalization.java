package ru.nsu.ga.grentseva.card.CardLocalization;

import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;
import ru.nsu.ga.grentseva.card.Card;

import java.util.List;


public class EnglishCardLocalization implements CardLocalization {

    @Override
    public String rank(CardRank rank) {
        return switch (rank) {
            case TWO -> "Two";
            case THREE -> "Three";
            case FOUR -> "Four";
            case FIVE -> "Five";
            case SIX -> "Six";
            case SEVEN -> "Seven";
            case EIGHT -> "Eight";
            case NINE -> "Nine";
            case TEN -> "Ten";
            case JACK -> "Jack";
            case QUEEN -> "Queen";
            case KING -> "King";
            case ACE -> "Ace";
        };
    }

    @Override
    public String suit(CardSuit suit) {
        return switch (suit) {
            case HEARTS -> "Hearts";
            case DIAMONDS -> "Diamonds";
            case CLUBS -> "Clubs";
            case SPADES -> "Spades";
        };
    }

    @Override
    public String suitAdjective(CardSuit suit, boolean female) {
        return "of " + suit(suit);
    }

    @Override
    public String formatCard(Card card) {
        if (card.getRank() == CardRank.QUEEN || card.getRank() == CardRank.JACK || card.getRank() == CardRank.KING || card.getRank() == CardRank.ACE){
            return rank(card.getRank()) + " of " + suit(card.getSuit()) + " (" + card.getRank().getValue() + ")";
        }
        return rank(card.getRank()) + " " + suit(card.getSuit()) + " (" + card.getRank().getValue() + ")";
    }

    @Override
    public String formatCardList(List<Card> cards) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cards.size(); i++) {
            sb.append(formatCard(cards.get(i)));
            if (i < cards.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String formatDealerCards(List<Card> cards, boolean hideSecondCard) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cards.size(); i++) {
            if (i == 1 && hideSecondCard) {
                sb.append("<hidden card>");
            } else {
                sb.append(formatCard(cards.get(i)));
            }
            if (i < cards.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }


}





