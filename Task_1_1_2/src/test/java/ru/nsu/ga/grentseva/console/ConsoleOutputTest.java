package ru.nsu.ga.grentseva.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.*;
import ru.nsu.ga.grentseva.card.CardLocalization.EnglishCardLocalization;
import ru.nsu.ga.grentseva.card.CardLocalization.RussianCardLocalization;
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
        Card.setLocalization(new RussianCardLocalization());
    }

    private String getOutput() {
        String text = outContent.toString().trim();
        outContent.reset();
        return text;
    }

    @Test
    void testPrintWelcomeRussian() {
        output.printWelcome();
        assertTrue(getOutput().contains("Добро пожаловать"));
    }

    @Test
    void testPrintPlayerCardsRussian() {
        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.DIAMONDS)
        );
        output.printPlayerCards(cards, 21);
        String text = getOutput();
        assertTrue(text.contains("Ваши карты"));
        assertTrue(text.contains("21"));
        for (Card card : cards) {
            assertTrue(text.contains(Card.getCardName(card)));
        }
    }

    @Test
    void testPrintRoundRussian() {
        output.printRound(5);
        assertTrue(getOutput().contains("Раунд"));
    }

    @Test
    void testPrintDealerCardsHiddenRussian() {
        List<Card> cards = List.of(
                new Card(CardRank.FIVE, CardSuit.HEARTS),
                new Card(CardRank.KING, CardSuit.CLUBS)
        );
        output.printDealerCardsHidden(cards);
        String text = getOutput();
        assertTrue(text.contains("Карты дилера"));
        assertTrue(text.contains("<закрытая карта>"));
    }

    @Test
    void testPrintDealerCardsOpenRussian() {
        List<Card> cards = List.of(
                new Card(CardRank.SEVEN, CardSuit.DIAMONDS),
                new Card(CardRank.QUEEN, CardSuit.HEARTS)
        );
        output.printDealerCardsOpen(cards, 17);
        String text = getOutput();
        assertTrue(text.contains("Карты дилера"));
        assertTrue(text.contains("17"));
        for (Card card : cards) {
            assertTrue(text.contains(Card.getCardName(card)));
        }
    }

    @Test
    void testPrintPlayerWinRussian() {
        output.printPlayerWin();
        assertTrue(getOutput().contains("Вы выиграли"));
    }

    @Test
    void testPrintDealerWinRussian() {
        output.printDealerWin();
        assertTrue(getOutput().contains("Дилер выиграл"));
    }

    @Test
    void testPrintDrawRussian() {
        output.printDraw();
        assertTrue(getOutput().contains("Ничья"));
    }

    @Test
    void testPrintScoreRussian() {
        output.printScore(3, 5);
        String text = getOutput();
        assertTrue(text.contains("Счёт"));
        assertTrue(text.contains("3"));
        assertTrue(text.contains("5"));
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
        Card.setLocalization(new EnglishCardLocalization());
    }

    private String getOutput() {
        String text = outContent.toString().trim();
        outContent.reset();
        return text;
    }

    @Test
    void testPrintWelcomeEnglish() {
        output.printWelcome();
        assertTrue(getOutput().toLowerCase().contains("welcome"));
    }

    @Test
    void testPrintPlayerCardsEnglish() {
        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.DIAMONDS)
        );
        output.printPlayerCards(cards, 21);
        String text = getOutput();
        assertTrue(text.contains("Your cards"));
        assertTrue(text.contains("21"));
        for (Card card : cards) {
            assertTrue(text.contains(Card.getCardName(card)));
        }
    }

    @BeforeEach
    void setupEnglish() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true));
        output = new ConsoleOutput(new EnglishLocalization());
        Card.setLocalization(new EnglishCardLocalization());
    }

    @Test
    void testPrintRoundEnglish() {
        output.printRound(5);
        assertTrue(getOutput().contains("Round 5"));
    }

    @Test
    void testPrintDealerCardsHiddenEnglish() {
        List<Card> cards = List.of(
                new Card(CardRank.FIVE, CardSuit.HEARTS),
                new Card(CardRank.KING, CardSuit.CLUBS)
        );
        output.printDealerCardsHidden(cards);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("dealer"));
        assertTrue(text.contains("<hidden card>"));
    }

    @Test
    void testPrintDealerCardsOpenEnglish() {
        List<Card> cards = List.of(
                new Card(CardRank.SEVEN, CardSuit.DIAMONDS),
                new Card(CardRank.QUEEN, CardSuit.HEARTS)
        );
        output.printDealerCardsOpen(cards, 17);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("dealer"));
        assertTrue(text.contains("17"));
        for (Card card : cards) {
            assertTrue(text.contains(Card.getCardName(card)));
        }
    }

    @Test
    void testPrintPlayerWinEnglish() {
        output.printPlayerWin();
        assertTrue(getOutput().toLowerCase().contains("you win"));
    }

    @Test
    void testPrintDealerWinEnglish() {
        output.printDealerWin();
        assertTrue(getOutput().toLowerCase().contains("dealer wins"));
    }

    @Test
    void testPrintDrawEnglish() {
        output.printDraw();
        assertTrue(getOutput().toLowerCase().contains("draw"));
    }

    @Test
    void testPrintScoreEnglish() {
        output.printScore(3, 5);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("score"));
        assertTrue(text.contains("3"));
        assertTrue(text.contains("5"));
    }

    @Test
    void testAllEnglishCards() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                Card card = new Card(rank, suit);
                String name = Card.getCardName(card);
                assertTrue(name.toLowerCase().contains(rank.name().toLowerCase()), "Rank missing: " + name);
                assertTrue(name.toLowerCase().contains(suit.name().toLowerCase()), "Suit missing: " + name);
                assertTrue(name.contains("(" + rank.getValue() + ")"), "Value missing: " + name);
            }
        }
    }
}
