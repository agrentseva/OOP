package ru.nsu.ga.grentseva.blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Random;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.console.ConsoleInput;
import ru.nsu.ga.grentseva.console.ConsoleOutput;
import ru.nsu.ga.grentseva.console.localization.RussianLocalization;
import ru.nsu.ga.grentseva.players.Player;

class RoundTest {

    private RussianLocalization localization = new RussianLocalization();
    private ConsoleOutput output = new ConsoleOutput(localization);
    private Random random = new Random();
    private int roundCounter = 0;
    private Player player = new Player();
    private Player dealer = new Player();

    private ConsoleInput input = new ConsoleInput(output) {
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

