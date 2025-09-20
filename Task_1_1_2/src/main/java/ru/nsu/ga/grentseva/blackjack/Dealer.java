package ru.nsu.ga.grentseva.blackjack;

public class Dealer extends Player {

    /**
     * Дилер добирает карты, пока сумма очков < 17.
     */
    public void dealerPlay(Deck deck) {
        while (getHandValue() < 17) {
            Card newCard = deck.take();
            addCard(newCard);

            System.out.println("Дилер открывает карту: " + newCard);
        }
    }
}
