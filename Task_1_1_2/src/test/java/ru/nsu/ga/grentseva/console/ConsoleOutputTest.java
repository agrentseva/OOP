package ru.nsu.ga.grentseva.console;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.Deck;
import ru.nsu.ga.grentseva.players.Dealer;
import ru.nsu.ga.grentseva.players.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleOutputTest {

    @Test
    void testConsoleOutput_RussianAndEnglish() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
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

        output.setUseEnglish(false);
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

        String textRu = testOut.toString();
        assertTrue(textRu.contains("Добро пожаловать"));
        assertTrue(textRu.contains("Раунд 1"));
        assertTrue(textRu.contains("Дилер раздал карты"));
        assertTrue(textRu.contains("Блэкджек"));
        assertTrue(textRu.contains("Ваш ход"));
        assertTrue(textRu.contains("Вы открыли карту"));
        assertTrue(textRu.contains("Перебор"));
        assertTrue(textRu.contains("Ход дилера"));
        assertTrue(textRu.contains("Дилер открывает карту"));
        assertTrue(textRu.contains("Дилер перебрал"));
        assertTrue(textRu.contains("Вы выиграли раунд"));
        assertTrue(textRu.contains("Вы проиграли раунд"));
        assertTrue(textRu.contains("Ничья"));
        assertTrue(textRu.contains("Счёт: 1:0"));
        assertTrue(textRu.contains("Неверный ввод"));
        assertTrue(textRu.contains("[") && textRu.contains("]"));
        assertTrue(textRu.contains("закрытая карта"));

        testOut.reset();
        output.setUseEnglish(true);

        output.printWelcome();
        output.printRoundStart(2);
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
        output.printScore(2, 1);
        output.printInvalidInput();

        String textEn = testOut.toString();
        assertTrue(textEn.contains("Welcome"));
        assertTrue(textEn.contains("Round 2"));
        assertTrue(textEn.contains("Dealer deals"));
        assertTrue(textEn.contains("Blackjack"));
        assertTrue(textEn.contains("Your turn"));
        assertTrue(textEn.contains("You opened"));
        assertTrue(textEn.contains("Bust"));
        assertTrue(textEn.contains("Dealer"));
        assertTrue(textEn.contains("You won"));
        assertTrue(textEn.contains("You lost"));
        assertTrue(textEn.contains("Draw"));
        assertTrue(textEn.contains("Score: 2:1"));
        assertTrue(textEn.contains("Invalid input"));
        assertTrue(textEn.contains("[") && textEn.contains("]"));
        assertTrue(textEn.contains("hidden card"));

        System.setOut(originalOut);
    }
}
