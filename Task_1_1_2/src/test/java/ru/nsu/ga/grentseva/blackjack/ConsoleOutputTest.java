package ru.nsu.ga.grentseva.blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class ConsoleOutputTest {

    @Test
    void testAllConsoleOutput() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
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
        output.showInitialCards(player, dealer);
        output.printPlayerBlackjack();
        output.printPlayerTurn();
        output.printPlayerCard(card, player, dealer);
        output.printPlayerBust();
        output.printDealerTurn(dealer, player);
        output.printDealerCard(card, dealer, player);
        output.printDealerBust();
        output.printPlayerWin();
        output.printDealerWin();
        output.printDraw();
        output.printScore(1, 0);
        output.printInvalidInput();
        
        String text = testOut.toString();
        
        assertTrue(text.contains("Добро пожаловать"));
        assertTrue(text.contains("Раунд 1"));
        assertTrue(text.contains("Дилер раздал карты"));
        assertTrue(text.contains("Блэкджек!"));
        assertTrue(text.contains("Ваш ход"));
        assertTrue(text.contains("Вы открыли карту"));
        assertTrue(text.contains("Перебор"));
        assertTrue(text.contains("Ход дилера"));
        assertTrue(text.contains("Дилер открывает карту"));
        assertTrue(text.contains("Дилер перебрал"));
        assertTrue(text.contains("Вы выиграли раунд"));
        assertTrue(text.contains("Вы проиграли раунд"));
        assertTrue(text.contains("Ничья"));
        assertTrue(text.contains("Счёт: 1:0"));
        assertTrue(text.contains("Неверный ввод"));

        System.setOut(originalOut);
    }
}


