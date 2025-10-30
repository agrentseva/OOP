package ru.nsu.ga.grentseva.blackjack;

import ru.nsu.ga.grentseva.card.CardLocalization.*;
import ru.nsu.ga.grentseva.console.ConsoleInput;
import ru.nsu.ga.grentseva.console.ConsoleOutput;
import ru.nsu.ga.grentseva.console.localization.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ru.nsu.ga.grentseva.card.Card.setLocalization;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Scanner in = new Scanner(System.in);
        System.out.println("Select language / Выберите язык:");
        System.out.println("1 — Русский");
        System.out.println("2 — English");
        System.out.print("> ");

        Localization localization;
        String playersChoiceOfLocalization = in.nextLine();

        if (playersChoiceOfLocalization.equals("2")) {
            EnglishCardLocalization cardLocalization = new EnglishCardLocalization();
            localization = new EnglishLocalization();
            setLocalization(cardLocalization);
        } else {
            RussianCardLocalization cardLocalization = new RussianCardLocalization();
            localization = new RussianLocalization();
            setLocalization(cardLocalization);
        }

        ConsoleOutput output = new ConsoleOutput(localization);
        ConsoleInput input = new ConsoleInput(output);


        Game game = new Game(input, output);
        game.start();

        in.close();
    }
}
