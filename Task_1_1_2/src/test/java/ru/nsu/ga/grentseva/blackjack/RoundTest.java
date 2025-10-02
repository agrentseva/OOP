package ru.nsu.ga.grentseva.blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

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
    void testResult() {
        Round round = new Round(roundCounter, player, dealer, input, output, random);
        Round.Result result = round.play();

        assertTrue(result == Round.Result.PLAYER_WIN
                || result == Round.Result.DEALER_WIN
                || result == Round.Result.DRAW
        );
    }
}

