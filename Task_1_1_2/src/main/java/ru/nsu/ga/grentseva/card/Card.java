package ru.nsu.ga.grentseva.card;

import ru.nsu.ga.grentseva.card.CardLocalization.CardLocalization;
import ru.nsu.ga.grentseva.card.CardLocalization.RussianCardLocalization;

import java.util.List;

public class Card {
    private final CardRank rank;
    private final CardSuit suit;

    public static CardLocalization cardLocalization = new RussianCardLocalization();

    public Card(CardRank rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static void setLocalization(CardLocalization localization) {
        cardLocalization = localization;
    }

    public int getCardValue() {
        return rank.getCardValue();
    }

    public CardRank getRank() {
        return rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public static String getCardName(Card card){
        return cardLocalization.formatCard(card);
    }

    public static String getCards(List<Card> cards){
        return cardLocalization.formatCardList(cards);
    }

    public static String getCards(List<Card> cards, boolean b){
        return cardLocalization.formatDealerCards(cards, b);
    }
}

