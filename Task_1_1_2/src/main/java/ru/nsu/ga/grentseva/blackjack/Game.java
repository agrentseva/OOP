package ru.nsu.ga.grentseva.blackjack;

import ru.nsu.ga.grentseva.console.ConsoleInput;
import ru.nsu.ga.grentseva.console.ConsoleOutput;
import ru.nsu.ga.grentseva.players.Player;

import java.util.Random;

public class Game {
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final Random random = new Random();

    private int playerScore = 0;
    private int dealerScore = 0;
    private int roundCounter = 0;

    private final Player player = new Player();
    private final Player dealer = new Player();

    public Game(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public void start() {
        output.printWelcome();
        boolean isRunningGame = true;

        while (isRunningGame) {
            roundCounter++;
            Round round = new Round(roundCounter, player, dealer, input, output, random);
            Round.Result result = round.play();

            switch (result) {
                case PLAYER_WIN -> playerScore++;
                case DEALER_WIN -> dealerScore++;
                case DRAW -> {
                    playerScore++;
                    dealerScore++;
                }
            }

            output.printScore(playerScore, dealerScore);
            isRunningGame = input.askPlayerWantsToContinueGame();
        }
    }
}


