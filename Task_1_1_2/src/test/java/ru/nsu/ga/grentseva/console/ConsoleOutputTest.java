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
import static ru.nsu.ga.grentseva.card.Card.getCardName;

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
        return outContent.toString();
    }

    @Test
    void testAllRussianCards() {
        Card.setLocalization(new EnglishCardLocalization());
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                Card card = new Card(rank, suit);
                String name = getCardName(card);

                assertTrue(name.toLowerCase().contains(rank.name().toLowerCase()));
                assertTrue(name.toLowerCase().contains(suit.name().toLowerCase()));
                assertTrue(name.contains("(" + rank.getValue() + ")"));
            }
        }
    }

    @Test
    void testAllRussianConsoleMessages() {
        Card.setLocalization(new RussianCardLocalization());

        output.printWelcome();
        assertTrue(getOutput().contains("Добро пожаловать"));

        output.printRound(1);
        assertTrue(getOutput().contains("Раунд 1"));

        output.printPlayerWin();
        assertTrue(getOutput().contains("Вы выиграли"));

        output.printDealerWin();
        assertTrue(getOutput().contains("Дилер выиграл"));

        output.printDraw();
        assertTrue(getOutput().contains("Ничья"));

        output.printScore(5, 3);
        String text = getOutput();
        assertTrue(text.contains("Счёт"));
        assertTrue(text.contains("5"));
        assertTrue(text.contains("3"));

        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.HEARTS)
        );

        output.printPlayerCards(cards, 21);
        assertTrue(getOutput().contains("Ваши карты"));
        output.printDealerCardsHidden(cards);
        assertTrue(getOutput().contains("<закрытая карта>"));
        output.printDealerCardsOpen(cards, 21);
        assertTrue(getOutput().contains("Карты дилера"));

        for (Card card : cards) {
            output.printPlayerDraws(card);
            output.printDealerDraws(card);
        }
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
    void testAllEnglishCards() {
        Card.setLocalization(new EnglishCardLocalization());
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                Card card = new Card(rank, suit);
                String name = getCardName(card);

                assertTrue(name.toLowerCase().contains(rank.name().toLowerCase()),
                        "Rank not found: " + name);
                assertTrue(name.toLowerCase().contains(suit.name().toLowerCase()),
                        "Suit not found: " + name);
                assertTrue(name.contains("(" + rank.getValue() + ")"), "Value not found: " + name);
            }
        }
    }

    @Test
    void testAllEnglishConsoleMessages() {
        Card.setLocalization(new EnglishCardLocalization());

        output.printWelcome();
        assertTrue(getOutput().toLowerCase().contains("welcome"));

        output.printRound(2);
        assertTrue(getOutput().toLowerCase().contains("round 2"));

        output.printPlayerWin();
        assertTrue(getOutput().toLowerCase().contains("you win"));

        output.printDealerWin();
        assertTrue(getOutput().toLowerCase().contains("dealer wins"));

        output.printDraw();
        assertTrue(getOutput().toLowerCase().contains("draw"));

        output.printScore(4, 6);
        String text = getOutput();
        assertTrue(text.contains("4"));
        assertTrue(text.contains("6"));

        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.HEARTS)
        );

        output.printPlayerCards(cards, 21);
        assertTrue(getOutput().toLowerCase().contains("your cards"));

        output.printDealerCardsHidden(cards);
        assertTrue(getOutput().toLowerCase().contains("dealer"));

        output.printDealerCardsOpen(cards, 21);
        assertTrue(getOutput().toLowerCase().contains("dealer"));

        for (Card card : cards) {
            output.printPlayerDraws(card);
            output.printDealerDraws(card);
        }
    }
}
