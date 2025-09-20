package ru.nsu.ga.grentseva.blackjack;

/**
 * Класс дилера, наследник игрока.
 * Содержит логику поведения дилера в игре.
 */
public class Dealer extends Player {

    public void dealerPlay(Deck deck) {
        while (getHandValue() < 17) {
            Card newCard = deck.take();
            addCard(newCard);

            System.out.println("Дилер открывает карту: " + newCard);
        }
    }
}
