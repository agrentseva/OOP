package ru.nsu.ga.grentseva.players;

import ru.nsu.ga.grentseva.card.Card;

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

    public boolean hasBlackjack() {
        return hand.hasBlackjack();
    }

    public void clearHand() {
        hand.clearHand();
    }
}

