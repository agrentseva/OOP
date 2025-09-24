package ru.nsu.ga.grentseva.blackjack;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        Game game = new Game(input, output);
        game.start();
    }
}
