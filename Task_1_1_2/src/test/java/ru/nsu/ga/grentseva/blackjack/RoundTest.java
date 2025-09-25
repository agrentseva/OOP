package ru.nsu.ga.grentseva.blackjack;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundTest {

    private ConsoleOutput output = new ConsoleOutput();
    private Random random = new Random();
    private int roundCounter = 0;
    private Player player = new Player();
    private Dealer dealer = new Dealer(output);

    private ConsoleInput input = new ConsoleInput() {
        @Override
        public int askPlayerChoice() {
            return 0;
        }
    };

    @Test
    void testPlayResultWinPlayer() {
        Round round = new Round(roundCounter, player, dealer, input, output, random);
        Round.Result result = round.play();

        if (player.hasBlackjack() || dealer.isBust() || player.getHandValue() > dealer.getHandValue()) {
            assertTrue(result == Round.Result.PLAYER_WIN);
        }
        else{
            assertTrue(result == Round.Result.DEALER_WIN || result == Round.Result.DRAW);
        }
    }

    @Test
    void testPlayResultWinDealer() {
        Round round = new Round(roundCounter, player, dealer, input, output, random);
        Round.Result result = round.play();

        if (player.getHandValue() < dealer.getHandValue()) {
            assertTrue(result == Round.Result.DEALER_WIN);
        } else {
            assertTrue(result == Round.Result.DRAW || result == Round.Result.PLAYER_WIN );
        }
    }

    @Test
    void testPlayResultDraw() {
        Round round = new Round(roundCounter, player, dealer, input, output, random);
        Round.Result result = round.play();

       if (player.getHandValue() == dealer.getHandValue()) {
            assertTrue(result == Round.Result.DRAW);
        } else {
           assertTrue(result == Round.Result.DEALER_WIN || result == Round.Result.PLAYER_WIN );
        }
    }

    @Test
    void testReversePlayResult() {
        Round round = new Round(roundCounter, player, dealer, input, output, random);
        Round.Result result = round.play();

        if (result == Round.Result.DRAW) {
            assertTrue(player.getHandValue() == dealer.getHandValue());
        } else {
            assertTrue(player.getHandValue() < dealer.getHandValue() || player.getHandValue() > dealer.getHandValue() );
        }

        if (result == Round.Result.PLAYER_WIN) {
            assertTrue( player.getHandValue() > dealer.getHandValue());
        } else {
            assertTrue(player.getHandValue() < dealer.getHandValue() || player.getHandValue() == dealer.getHandValue());
        }

        if (result == Round.Result.DEALER_WIN) {
            assertTrue(player.getHandValue() < dealer.getHandValue());
        } else {
            assertTrue(player.getHandValue() > dealer.getHandValue() || player.getHandValue() == dealer.getHandValue());
        }


    }
}





