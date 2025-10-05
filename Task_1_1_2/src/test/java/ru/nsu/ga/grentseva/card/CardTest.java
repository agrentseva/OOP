package ru.nsu.ga.grentseva.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        String nameRu = card.getCardName(false); // русский
        assertTrue(nameRu.contains("Дама"));
        assertTrue(nameRu.contains("Бубновая"));
    }

    @Test
    void testCardNameQueenEnglish() {
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        String nameEn = card.getCardName(true); // английский
        assertTrue(nameEn.contains("Queen"));
        assertTrue(nameEn.contains("of Diamonds"));
    }

    @Test
    void testGetCardNameNumberCardRussian() {
        Card card = new Card(CardRank.TEN, CardSuit.DIAMONDS);
        String nameRu = card.getCardName(false);
        assertTrue(nameRu.contains("Десятка"));
        assertTrue(nameRu.contains("Бубен"));
    }

    @Test
    void testGetCardNameNumberCardEnglish() {
        Card card = new Card(CardRank.TEN, CardSuit.DIAMONDS);
        String nameEn = card.getCardName(true);
        assertTrue(nameEn.contains("Ten"));
        assertTrue(nameEn.contains("of Diamonds"));
    }

    @Test
    void testGetCardNameJackRussian() {
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);
        String nameRu = card.getCardName(false);
        assertTrue(nameRu.contains("Валет"));
        assertTrue(nameRu.contains("Трефовый"));
    }

    @Test
    void testGetCardNameJackEnglish() {
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);
        String nameEn = card.getCardName(true);
        assertTrue(nameEn.contains("Jack"));
        assertTrue(nameEn.contains("of Clubs"));
    }

    @Test
    void testToStringDefaultRussian() {
        Card card = new Card(CardRank.SEVEN, CardSuit.CLUBS);
        assertEquals(card.getCardName(false), card.toString());
    }

}
