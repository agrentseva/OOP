package ru.nsu.ga.grentseva.blackjack;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleOutputTest {

    @Test
    void testConsoleOutput() {
        // Перехватываем System.out
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        ConsoleOutput output = new ConsoleOutput();
        Player player = new Player();
        Dealer dealer = new Dealer(output);
        Deck deck = new Deck(1);
        deck.shuffle();

        player.addCard(deck.take());
        player.addCard(deck.take());
        dealer.addCard(deck.take());
        dealer.addCard(deck.take());
        Card card = deck.take();

        output.printWelcome();
        output.printRoundStart(1);
        output.printPlayerTurn();
        output.printPlayerCard(card, player, dealer);
        output.printScore(1, 0);

        String text = testOut.toString();
        assertTrue(text.contains("Добро пожаловать в Блэкджек!"));
        assertTrue(text.contains("\nРаунд 1"));
        assertTrue(text.contains("\nВаш ход\n-------"));
        assertTrue(text.contains("Вы открыли карту"));
        assertTrue(text.contains("Счёт: 1:0"));

        System.setOut(System.out);
    }
}

