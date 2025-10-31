package ru.nsu.ga.grentseva.blackjack;

import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.Deck;
import ru.nsu.ga.grentseva.console.ConsoleInput;
import ru.nsu.ga.grentseva.console.ConsoleOutput;
import ru.nsu.ga.grentseva.players.Dealer;
import ru.nsu.ga.grentseva.players.Player;

import java.util.Random;

public class Round {
    public enum Result { PLAYER_WIN, DEALER_WIN, DRAW }

    private final int roundNumber;
    private final Player player;
    private final Dealer dealer;
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final Random random;

    public Round(int roundNumber, Player player, Dealer dealer,
                 ConsoleInput input, ConsoleOutput output, Random random) {
        this.roundNumber = roundNumber;
        this.player = player;
        this.dealer = dealer;
        this.input = input;
        this.output = output;
        this.random = random;
    }

    public Result play() {
        output.printRoundStart(roundNumber);

        Deck deck = new Deck(random.nextInt(5) + 1);
        deck.shuffle();

        player.clearHand();
        dealer.clearHand();

        player.addCard(deck.take());
        player.addCard(deck.take());
        dealer.addCard(deck.take());
        dealer.addCard(deck.take());

        output.showInitialCards(player, dealer);

        if (player.hasBlackjack()) {
            output.printPlayerBlackjack();
            return Result.PLAYER_WIN;
        }

        if (playerTurn(deck)) {
            return Result.DEALER_WIN;
        }

        dealerTurn(deck);
        return determineWinner();
    }

    private boolean playerTurn(Deck deck) {
        output.printPlayerTurn();
        while (true) {
            int choice = input.askPlayerChoice();
            if (choice == 1) {
                Card card = deck.take();
                player.addCard(card);
                output.printPlayerCard(card, player, dealer);
                if (player.isBust()) {
                    output.printPlayerBust();
                    return true;
                }
            } else if (choice == 0) {
                return false;
            } else {
                output.printInvalidInput();
            }
        }
    }

    private void dealerTurn(Deck deck) {
        dealer.dealerTurn(deck, player);
    }

    private Result determineWinner() {
        if (dealer.isBust()) {
            output.printDealerBust();
            return Result.PLAYER_WIN;
        }
        if (player.getHandValue() > dealer.getHandValue()) {
            output.printPlayerWin();
            return Result.PLAYER_WIN;
        }
        if (player.getHandValue() < dealer.getHandValue()) {
            output.printDealerWin();
            return Result.DEALER_WIN;
        }
        output.printDraw();
        return Result.DRAW;
    }
}



