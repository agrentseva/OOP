package ru.nsu.ga.grentseva.card.CardLocalization;

import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;
import ru.nsu.ga.grentseva.card.Card;

import java.util.List;

public interface CardLocalization {
    String rank(CardRank rank);
    String suit(CardSuit suit);
    String suitAdjective(CardSuit suit, boolean female);
    String formatCard(Card card);
    String formatCardList(List<Card> cards);
    String formatDealerCards(List<Card> cards, boolean hideSecondCard);
}
