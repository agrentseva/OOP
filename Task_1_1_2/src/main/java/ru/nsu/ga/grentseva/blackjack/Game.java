package ru.nsu.ga.grentseva.blackjack;

import java.util.Random;

public class Game {
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final Random random = new Random();

    private int playerScore = 0;
    private int dealerScore = 0;
    private int roundCounter = 0;

    private final Player player = new Player();
    private final Dealer dealer;

    public Game(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;

        dealer = new Dealer(output);
    }

    public void start() {
        output.printWelcome();
        boolean game = true;

        while (game) {
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
                default -> throw new IllegalStateException("Unexpected value: " + result);
            }

            output.printScore(playerScore, dealerScore);
            game = input.askContinue();
        }
    }
}


