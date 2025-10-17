package ru.nsu.ga.grentseva.card.CardLocalization;

import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;

import java.util.List;

public class RussianCardLocalization implements CardLocalization {

    @Override
    public String rank(CardRank rank) {
        return switch (rank) {
            case TWO -> "Двойка";
            case THREE -> "Тройка";
            case FOUR -> "Четвёрка";
            case FIVE -> "Пятёрка";
            case SIX -> "Шестёрка";
            case SEVEN -> "Семёрка";
            case EIGHT -> "Восьмёрка";
            case NINE -> "Девятка";
            case TEN -> "Десятка";
            case JACK -> "Валет";
            case QUEEN -> "Дама";
            case KING -> "Король";
            case ACE -> "Туз";
        };
    }

    @Override
    public String suit(CardSuit suit) {
        return switch (suit) {
            case HEARTS -> "Червей";
            case DIAMONDS -> "Бубен";
            case CLUBS -> "Треф";
            case SPADES -> "Пики";
        };
    }

    @Override
    public String suitAdjective(CardSuit suit, boolean female) {
        return switch (suit) {
            case HEARTS -> female ? "Червонная" : "Червонный";
            case DIAMONDS -> female ? "Бубновая" : "Бубновый";
            case CLUBS -> female ? "Трефовая" : "Трефовый";
            case SPADES -> female ? "Пиковая" : "Пиковый";
        };
    }

    @Override
    public String formatCard(Card card) {
        if (card.getRank() == CardRank.QUEEN) {
            return suitAdjective(card.getSuit(), true) + " " + rank(card.getRank()) +
                    " (" + card.getRank().getCardValue() + ")";
        }
        if (card.getRank() == CardRank.KING || card.getRank() == CardRank.JACK) {
            return suitAdjective(card.getSuit(), false) + " " + rank(card.getRank()) +
                    " (" + card.getRank().getCardValue() + ")";
        }
        return rank(card.getRank()) + " " + suit(card.getSuit()) +
                " (" + card.getRank().getCardValue() + ")";
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
                sb.append("<закрытая карта>");
            } else {
                sb.append(formatCard(cards.get(i)));
            }
            if (i < cards.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

