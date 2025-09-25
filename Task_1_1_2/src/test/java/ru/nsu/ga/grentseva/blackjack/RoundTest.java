package ru.nsu.ga.grentseva.blackjack;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RoundTest {

    private ConsoleOutput output = new ConsoleOutput();
    private Random random = new Random(); // фиксируем сид для детерминизма
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
    void testPlayResult() {
        Round round = new Round(roundCounter, player, dealer, input, output, random);
        Round.Result result = round.play();

        if (player.hasBlackjack()){
            assertTrue(result == Round.Result.PLAYER_WIN);
        }
        else if (dealer.isBust()) {
            assertTrue(result == Round.Result.PLAYER_WIN);
        }
        else if (player.getHandValue() > dealer.getHandValue()) {
            assertTrue(result == Round.Result.PLAYER_WIN);
        }
        else if (player.getHandValue() < dealer.getHandValue()) {
            assertTrue(result == Round.Result.DEALER_WIN);
        }
        else{
            assertTrue(result == Round.Result.DRAW);
        }
    }
}


