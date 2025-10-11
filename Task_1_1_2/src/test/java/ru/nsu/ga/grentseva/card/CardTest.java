package ru.nsu.ga.grentseva.card;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.CardLocalization.EnglishCardLocalization;
import ru.nsu.ga.grentseva.card.CardLocalization.RussianCardLocalization;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardValues() {
        Card aceOfSpades = new Card(CardRank.ACE, CardSuit.SPADES);
        assertEquals(11, aceOfSpades.getCardValue());

        Card kingOfHearts = new Card(CardRank.KING, CardSuit.HEARTS);
        assertEquals(10, kingOfHearts.getCardValue());
    }

    @Test
    void testCardNameQueenRussian() {
        Card.setLocalization(new RussianCardLocalization());
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);

        String nameRu = Card.getCardName(card);
        assertTrue(nameRu.contains("Дама"), "Ожидалось слово 'Дама'");
        assertTrue(nameRu.contains("Бубнов"), "Ожидалась масть 'Бубновая'");
        assertTrue(nameRu.contains("(10)"));
    }

    @Test
    void testCardNameJackRussian() {
        Card.setLocalization(new RussianCardLocalization());
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);

        String nameRu = Card.getCardName(card);
        assertTrue(nameRu.contains("Валет"));
        assertTrue(nameRu.contains("Треф"));
        assertTrue(nameRu.contains("(10)"));
    }

    @Test
    void testCardNameQueenEnglish() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);

        String nameEn = Card.getCardName(card);
        assertTrue(nameEn.toLowerCase().contains("queen"));
        assertTrue(nameEn.toLowerCase().contains("diamonds"));
        assertTrue(nameEn.contains("(10)"));
    }

    @Test
    void testCardNameJackEnglish() {
        Card.setLocalization(new EnglishCardLocalization());
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);

        String nameEn = Card.getCardName(card);
        assertTrue(nameEn.toLowerCase().contains("jack"));
        assertTrue(nameEn.toLowerCase().contains("clubs"));
        assertTrue(nameEn.contains("(10)"));
    }
}
