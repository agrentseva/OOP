package ru.nsu.ga.grentseva.console;

import java.util.Scanner;


public class ConsoleInput {

    private final Scanner in = new Scanner(System.in);
    private final ConsoleOutput output;

    public ConsoleInput(ConsoleOutput output) {
        this.output = output;
    }

    public int askPlayerToStopOrTakeCard() {
        output.askPlayerToStopOrTakeCard();
        while (true) {
            int choice = in.nextInt();
            if (choice == 0 || choice == 1) {
                return choice;
            } else {
                output.printInvalidValue();
            }
        }
    }

    public boolean askPlayerWantsToContinueGame() {
        output.askPlayerWantsToContinueGame();
        while (true) {
            int choice = in.nextInt();
            if (choice == 1) {
                return true;
            } else if (choice == 0) {
                closeInput();
                return false;
            } else {
                output.printInvalidValue();
            }
        }
    }

    public void closeInput() {
        in.close();
    }
}
