package ru.nsu.ga.grentseva.Task_1_1_2;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Hand hand;

    public Player() {
        hand = new Hand(); // инициализируем руку
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

class Dealer extends Player{

    public void dealerPlay(Deck deck) {
        while (getHandValue() < 17) {
            Card newCard = deck.take();
            addCard(newCard);

            System.out.println("Дилер открывает карту: " + newCard);

        }
    }
}