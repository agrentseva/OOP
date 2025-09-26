package ru.nsu.ga.grentseva.blackjack;

import java.util.Scanner;

public class ConsoleInput {
    private final Scanner in = new Scanner(System.in);

    public int askPlayerChoice() {
        System.out.println("Введите 1 - взять карту, 0 - остановиться:");
        return in.nextInt();
    }

    public boolean askContinue() {
        System.out.println("\nХотите сыграть ещё один раунд? (1 - да, 0 - нет):");
        while (true) {
            int choice = in.nextInt();
            if (choice == 1) {
                return true;
            }
            if (choice == 0) {
                return false;
            }
            System.out.println("Неверный ввод.");
        }
    }
}
