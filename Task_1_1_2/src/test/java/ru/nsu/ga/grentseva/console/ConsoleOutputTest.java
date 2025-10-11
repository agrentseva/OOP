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
    void testPrintPlayerCards() {
        List<Card> cards = List.of(
                new Card(CardRank.SEVEN, CardSuit.HEARTS),
                new Card(CardRank.KING, CardSuit.SPADES)
        );
        output.printPlayerCards(cards, 17);
        String text = getOutput();
        assertTrue(text.contains("Ваши карты"));
        assertTrue(text.contains("Черв"));
        assertTrue(text.contains("Король"));
        assertTrue(text.contains("17"));
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
    void testPrintPlayerCards() {
        List<Card> cards = List.of(
                new Card(CardRank.FIVE, CardSuit.DIAMONDS),
                new Card(CardRank.ACE, CardSuit.SPADES)
        );
        output.printPlayerCards(cards, 16);
        String text = getOutput();
        assertTrue(text.contains("Your cards"));
        assertTrue(text.toLowerCase().contains("five"));
        assertTrue(text.toLowerCase().contains("diamonds"));
        assertTrue(text.contains("16"));
    }

    @Test
    void testPrintDealerCardsHidden() {
        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.HEARTS),
                new Card(CardRank.TEN, CardSuit.SPADES)
        );
        output.printDealerCardsHidden(cards);
        String text = getOutput();
        assertTrue(text.contains("Dealer cards"));
        assertTrue(text.contains("<hidden card>"));
    }

    @Test
    void testPrintDealerCardsOpen() {
        List<Card> cards = List.of(
                new Card(CardRank.NINE, CardSuit.CLUBS),
                new Card(CardRank.SEVEN, CardSuit.HEARTS)
        );
        output.printDealerCardsOpen(cards, 16);
        String text = getOutput();
        assertTrue(text.contains("Dealer cards"));
        assertTrue(text.contains("16"));
    }

    @Test
    void testPrintDealerReveals() {
        Card card = new Card(CardRank.KING, CardSuit.HEARTS);
        output.printDealerReveals(card);
        assertTrue(getOutput().contains("Dealer reveals"));
    }

    @Test
    void testPrintPlayerDraws() {
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        output.printPlayerDraws(card);
        assertTrue(getOutput().contains("You draw"));
        assertTrue(getOutput().toLowerCase().contains("queen"));
    }

    @Test
    void testPrintDealerDraws() {
        Card card = new Card(CardRank.SEVEN, CardSuit.CLUBS);
        output.printDealerDraws(card);
        assertTrue(getOutput().contains("Dealer draws"));
        assertTrue(getOutput().toLowerCase().contains("seven"));
    }
}
