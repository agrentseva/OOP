package ru.nsu.ga.grentseva.console;

import ru.nsu.ga.grentseva.console.localization.Localization;
import java.util.Scanner;


public class ConsoleInput {

    private final Scanner in = new Scanner(System.in);
    private final ConsoleOutput output;

    public ConsoleInput(ConsoleOutput output) {
        this.output = output;
    }

    public int askPlayerChoice() {
        output.printAskPlayer();
        while (true) {
            int choice = in.nextInt();
            if (choice == 0 || choice == 1) {
                return choice;
            } else {
                output.printInvalidValue();
            }
        }
    }

    public boolean askContinue() {
        output.printAskContinue();
        while (true) {
            int choice = in.nextInt();
            if (choice == 1) {
                return true;
            } else if (choice == 0) {
                close();
                return false;
            } else {
                output.printInvalidValue();
            }
        }
    }

    public void close() {
        in.close();
    }
}
