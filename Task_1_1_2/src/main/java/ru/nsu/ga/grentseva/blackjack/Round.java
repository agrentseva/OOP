package ru.nsu.ga.grentseva.blackjack;

import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.Deck;
import ru.nsu.ga.grentseva.console.ConsoleInput;
import ru.nsu.ga.grentseva.console.ConsoleOutput;
import ru.nsu.ga.grentseva.players.Player;

import java.util.Random;

public class Round {
    public enum Result { PLAYER_WIN, DEALER_WIN, DRAW }

    private int roundNumber;
    private final Player player;
    private final Player dealer;
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final Random random;

    public Round(int roundNumber, Player player, Player dealer,
                 ConsoleInput input, ConsoleOutput output, Random random) {
        this.roundNumber = roundNumber;
        this.player = player;
        this.dealer = dealer;
        this.input = input;
        this.output = output;
        this.random = random;
    }

    public Result play() {
        output.printRound(roundNumber);
        Deck deck = new Deck(random.nextInt(5) + 1);
        deck.shuffle();

        player.clearHand();
        dealer.clearHand();

        player.addCard(deck.take());
        player.addCard(deck.take());
        dealer.addCard(deck.take());
        dealer.addCard(deck.take());

        output.printPlayerCards(player.getHandCards(), player.getHandValue());
        output.printDealerCardsHidden(dealer.getHandCards());

        if (player.hasBlackjack()) {
            output.printBlackjackPlayer();
            return Result.PLAYER_WIN;
        }

        if (playerTurn(deck)) {
            if (dealerTurn(deck)){
                return Result.DEALER_WIN;
            }
        }

        return determineWinner();
    }

    private boolean playerTurn(Deck deck) {
        while (true) {
            int choice = input.askPlayerToStopOrTakeCard();
            if (choice == 1) {
                Card newCard = deck.take();
                player.addCard(newCard);
                output.printPlayerDraws(newCard);
                output.printPlayerCards(player.getHandCards(), player.getHandValue());
                output.printDealerCardsHidden(dealer.getHandCards());

                if (player.getHandValue() >= 21) {
                    return false;
                }
            } else if (choice == 0) {
                return true;
            } else {
                output.askPlayerWantsToContinueGame();
            }
        }
    }

    private boolean dealerTurn(Deck deck) {
        output.printDealerReveals(dealer.getHandCards().get(1));
        if (dealer.hasBlackjack()) {
            output.printBlackjackDealer();
            return true;
        }
        output.printPlayerCards(player.getHandCards(), player.getHandValue());
        output.printDealerCardsOpen(dealer.getHandCards(), dealer.getHandValue());

        while (dealer.getHandValue() < 17) {
            Card newCard = deck.take();
            dealer.addCard(newCard);
            output.printDealerDraws(newCard);
            output.printPlayerCards(player.getHandCards(), player.getHandValue());
            output.printDealerCardsOpen(dealer.getHandCards(), dealer.getHandValue());
        }
        return false;
    }

    private Result determineWinner() {
        if (player.getHandValue() > 21) {
            output.printPlayerBust();
            return Result.DEALER_WIN;
        }

        if (dealer.getHandValue() > 21) {
            output.printDealerBust();
            return Result.PLAYER_WIN;
        }

        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        if (playerValue > dealerValue) {
            output.printPlayerWin();
            return Result.PLAYER_WIN;
        } else if (playerValue < dealerValue) {
            output.printDealerWin();
            return Result.DEALER_WIN;
        } else {
            output.printDraw();
            return Result.DRAW;
        }
    }
}
