package ru.nsu.ga.grentseva.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;
import ru.nsu.ga.grentseva.console.localization.EnglishLocalization;
import ru.nsu.ga.grentseva.console.localization.RussianLocalization;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleOutputRussianTest {

    private ByteArrayOutputStream outContent;
    private ConsoleOutput output;

    @BeforeEach
    void setup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true));
        output = new ConsoleOutput(new RussianLocalization());
    }

    private String getOutput() {
        return outContent.toString().trim();
    }

    @Test
    void testPrintWelcome() {
        output.printWelcome();
        assertTrue(getOutput().contains("Добро пожаловать"));
    }

    @Test
    void testPrintRound() {
        output.printRound(3);
        assertTrue(getOutput().contains("Раунд 3"));
    }

    @Test
    void testPrintDealerCardsHidden() {
        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.DIAMONDS)
        );
        output.printDealerCardsHidden(cards);
        String text = getOutput();
        assertTrue(text.contains("Карты дилера"));
        assertTrue(text.contains("<закрытая карта>"));
    }

    @Test
    void testPrintDealerCardsOpen() {
        List<Card> cards = List.of(
                new Card(CardRank.TEN, CardSuit.HEARTS),
                new Card(CardRank.EIGHT, CardSuit.CLUBS)
        );
        output.printDealerCardsOpen(cards, 18);
        String text = getOutput();
        assertTrue(text.contains("Карты дилера"));
        assertTrue(text.contains("18"));
    }

    @Test
    void testPrintPlayerWin() {
        output.printPlayerWin();
        assertTrue(getOutput().contains("Вы выиграли"));
    }

    @Test
    void testPrintDealerWin() {
        output.printDealerWin();
        assertTrue(getOutput().contains("Дилер выиграл"));
    }

    @Test
    void testPrintDraw() {
        output.printDraw();
        assertTrue(getOutput().contains("Ничья"));
    }

    @Test
    void testPrintScore() {
        output.printScore(2, 1);
        String text = getOutput();
        assertTrue(text.contains("Счёт"));
        assertTrue(text.contains("2"));
        assertTrue(text.contains("1"));
    }
}

class ConsoleOutputEnglishTest {

    private ByteArrayOutputStream outContent;
    private ConsoleOutput output;

    @BeforeEach
    void setup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true));
        output = new ConsoleOutput(new EnglishLocalization());
    }

    private String getOutput() {
        return outContent.toString().trim();
    }

    @Test
    void testPrintWelcome() {
        output.printWelcome();
        assertTrue(getOutput().contains("Welcome"));
    }

    @Test
    void testPrintRound() {
        output.printRound(2);
        assertTrue(getOutput().contains("Round 2"));
    }

    @Test
    void testPrintPlayerWin() {
        output.printPlayerWin();
        assertTrue(getOutput().contains("You win"));
    }

    @Test
    void testPrintDealerWin() {
        output.printDealerWin();
        assertTrue(getOutput().contains("Dealer wins"));
    }

    @Test
    void testPrintDealerBust() {
        output.printDealerBust();
        assertTrue(getOutput().contains("Dealer busts"));
    }

    @Test
    void testPrintPlayerBust() {
        output.printPlayerBust();
        assertTrue(getOutput().contains("You bust"));
    }

    @Test
    void testPrintScore() {
        output.printScore(3, 5);
        String text = getOutput();
        assertTrue(text.contains("Score"));
        assertTrue(text.contains("3"));
        assertTrue(text.contains("5"));
    }

    @Test
    void testPrintAskContinue() {
        output.printAskContinue();
        assertTrue(getOutput().contains("Enter"));
    }

    @Test
    void testPrintPlayerDraws() {
        Card card = new Card(CardRank.FOUR, CardSuit.HEARTS);
        output.printPlayerDraws(card);
        assertTrue(getOutput().contains("You draw"));
    }

    @Test
    void testPrintDealerDraws() {
        Card card = new Card(CardRank.KING, CardSuit.DIAMONDS);
        output.printDealerDraws(card);
        assertTrue(getOutput().contains("Dealer draws"));
    }

    @Test
    void testPrintDealerReveals() {
        Card card = new Card(CardRank.TEN, CardSuit.SPADES);
        output.printDealerReveals(card);
        assertTrue(getOutput().contains("Dealer reveals"));
    }

    @Test
    void testPrintBlackjackPlayer() {
        output.printBlackjackPlayer();
        assertTrue(getOutput().contains("You win"));
    }

    @Test
    void testPrintBlackjackDealer() {
        output.printBlackjackDealer();
        assertTrue(getOutput().contains("BLACKJACK"));
    }

    @Test
    void testPrintInvalidValue() {
        output.printInvalidValue();
        assertTrue(getOutput().contains("Invalid input"));
    }

    @Test
    void testPrintAskPlayer() {
        output.printAskPlayer();
        assertTrue(getOutput().contains("Enter"));
    }
}

