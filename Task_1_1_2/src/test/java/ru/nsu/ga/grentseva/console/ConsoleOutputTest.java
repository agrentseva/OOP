package ru.nsu.ga.grentseva.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.CardLocalization.EnglishCardLocalization;
import ru.nsu.ga.grentseva.card.CardLocalization.RussianCardLocalization;
import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;
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
    void testPrintWelcome() {
        Card.setLocalization(new RussianCardLocalization());
        output.printWelcome();
        assertTrue(getOutput().contains("Добро пожаловать"));
    }

    @Test
    void testPrintRound() {
        Card.setLocalization(new RussianCardLocalization());
        output.printRound(3);
        assertTrue(getOutput().contains("Раунд 3"));
    }

    @Test
    void testPrintPlayerCards() {
        Card.setLocalization(new RussianCardLocalization());
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
        Card.setLocalization(new RussianCardLocalization());
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
        Card.setLocalization(new RussianCardLocalization());
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
        Card.setLocalization(new RussianCardLocalization());
        output.printPlayerWin();
        assertTrue(getOutput().contains("Вы выиграли"));
    }

    @Test
    void testPrintDealerWin() {
        Card.setLocalization(new RussianCardLocalization());
        output.printDealerWin();
        assertTrue(getOutput().contains("Дилер выиграл"));
    }

    @Test
    void testPrintDraw() {
        Card.setLocalization(new RussianCardLocalization());
        output.printDraw();
        assertTrue(getOutput().contains("Ничья"));
    }

    @Test
    void testPrintScore() {
        Card.setLocalization(new RussianCardLocalization());
        output.printScore(2, 1);
        String text = getOutput();
        assertTrue(text.contains("Счёт"));
        assertTrue(text.contains("2"));
        assertTrue(text.contains("1"));
    }

    @Test
    void testCardNameQueenRussian() {
        Card.setLocalization(new RussianCardLocalization());
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        String nameRu = getCardName(card);
        assertTrue(nameRu.contains("Дама"));
        assertTrue(nameRu.contains("Бубновая"));
    }

    @Test
    void testGetCardNameNumberCardRussian() {
        Card.setLocalization(new RussianCardLocalization());
        Card card = new Card(CardRank.TEN, CardSuit.DIAMONDS);
        String nameRu = getCardName(card);
        assertTrue(nameRu.contains("Десятка"));
        assertTrue(nameRu.contains("Бубен"));
    }

    @Test
    void testGetCardNameJackRussian() {
        Card.setLocalization(new RussianCardLocalization());
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);
        String nameRu = getCardName(card);
        assertTrue(nameRu.contains("Валет"));
        assertTrue(nameRu.contains("Трефовый"));
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
    void testPrintPlayerCards() {
        Card.setLocalization(new EnglishCardLocalization());
        List<Card> cards = List.of(
                new Card(CardRank.TEN, CardSuit.HEARTS),
                new Card(CardRank.SEVEN, CardSuit.CLUBS)
        );
        output.printPlayerCards(cards, 17);
        String text = getOutput();
        assertTrue(text.contains("Your cards"));
        assertTrue(text.contains("17"));
        assertTrue(text.toLowerCase().contains("hearts"));
        assertTrue(text.toLowerCase().contains("clubs"));
    }

    @Test
    void testPrintDealerCardsHidden() {
        Card.setLocalization(new EnglishCardLocalization());
        List<Card> cards = List.of(
                new Card(CardRank.ACE, CardSuit.SPADES),
                new Card(CardRank.TEN, CardSuit.DIAMONDS)
        );
        output.printDealerCardsHidden(cards);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("dealer"));
        assertTrue(text.contains("<hidden card>"));
    }

    @Test
    void testPrintDealerCardsOpen() {
        Card.setLocalization(new EnglishCardLocalization());
        List<Card> cards = List.of(
                new Card(CardRank.KING, CardSuit.HEARTS),
                new Card(CardRank.EIGHT, CardSuit.CLUBS)
        );
        output.printDealerCardsOpen(cards, 18);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("dealer"));
        assertTrue(text.contains("18"));
        assertTrue(text.toLowerCase().contains("hearts"));
        assertTrue(text.toLowerCase().contains("clubs"));
    }

    @Test
    void testPrintPlayerDraws() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.FOUR, CardSuit.HEARTS);
        output.printPlayerDraws(card);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("you draw") || text.toLowerCase().contains("player draws"));
        assertTrue(text.toLowerCase().contains("4") || text.toLowerCase().contains("four"));
        assertTrue(text.toLowerCase().contains("hearts"));
    }

    @Test
    void testPrintDealerDraws() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.KING, CardSuit.DIAMONDS);
        output.printDealerDraws(card);
        String text = getOutput();
        assertTrue(text.toLowerCase().contains("dealer draws"));
        assertTrue(text.toLowerCase().contains("king"));
        assertTrue(text.toLowerCase().contains("diamonds"));
    }

    @Test
    void testCardNameQueenEnglish() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        String nameEn = getCardName(card);
        assertTrue(nameEn.toLowerCase().contains("queen"));
        assertTrue(nameEn.toLowerCase().contains("diamonds"));
        assertTrue(nameEn.contains("(10)"));
    }

    @Test
    void testGetCardNameNumberCardEnglish() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.TEN, CardSuit.DIAMONDS);
        String nameEn = getCardName(card);
        assertTrue(nameEn.toLowerCase().contains("ten"));
        assertTrue(nameEn.toLowerCase().contains("diamonds"));
        assertTrue(nameEn.contains("(10)"));
    }

    @Test
    void testGetCardNameJackEnglish() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);
        String nameEn = getCardName(card);
        assertTrue(nameEn.toLowerCase().contains("jack"));
        assertTrue(nameEn.toLowerCase().contains("clubs"));
        assertTrue(nameEn.contains("(10)"));
    }
}
