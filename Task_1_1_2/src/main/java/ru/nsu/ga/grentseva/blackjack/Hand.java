package ru.nsu.ga.grentseva.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int getHandValue() {
        int sum = 0;
        int countAce = 0;

        for (Card element : cards) {
            if (element.getCardValue() == 11) { // если туз
                countAce++;
            }
            sum += element.getCardValue();
        }

        while (sum > 21 && countAce > 0) {
            sum -= 10;
            countAce--;
        }

        return sum;
    }

    public boolean isBust() {
        return getHandValue() > 21;
    }

    public boolean hasBlackjack() {
        return getHandValue() == 21 && cards.size() == 2;
    }

    public void clearHand() {
        cards.clear();
    }
}