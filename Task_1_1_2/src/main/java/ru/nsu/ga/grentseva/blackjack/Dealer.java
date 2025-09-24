package ru.nsu.ga.grentseva.blackjack;

/**
 * Класс дилера, наследник игрока.
 * Содержит логику поведения дилера в игре.
 */
public class Dealer extends Player {

    private final ConsoleOutput output;

    public Dealer(ConsoleOutput output) {
        this.output = output;
    }

    public void dealerTurn(Deck deck, Player player) {
        output.printDealerTurn(this, player);
        while (getHandValue() < 17) {
            Card newCard = deck.take();
            addCard(newCard);
            output.printDealerCard(this, player);
        }
    }
}
