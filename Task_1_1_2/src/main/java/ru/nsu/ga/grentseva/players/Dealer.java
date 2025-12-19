package ru.nsu.ga.grentseva.players;

import ru.nsu.ga.grentseva.console.ConsoleOutput;
import ru.nsu.ga.grentseva.card.Deck;
import ru.nsu.ga.grentseva.card.Card;

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
            output.printDealerCard(newCard, this, player);
        }
    }
}
