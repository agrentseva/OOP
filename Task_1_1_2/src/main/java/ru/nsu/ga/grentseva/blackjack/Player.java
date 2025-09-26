package ru.nsu.ga.grentseva.blackjack;

import java.util.List;

public class Player {

    private Hand hand;

    public Player() {
        hand = new Hand();
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public List<Card> getHandCards() {
        return hand.getCards();
    }

    public int getHandValue() {
        return hand.getHandValue();
    }

    public boolean isBust() {
        return hand.isBust();
    }

    public boolean hasBlackjack() {
        return hand.hasBlackjack();
    }

    public void clearHand() {
        hand.clearHand();
    }
}

