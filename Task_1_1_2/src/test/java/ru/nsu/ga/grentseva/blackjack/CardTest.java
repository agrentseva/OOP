package ru.nsu.ga.grentseva.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class CardTest {

    @Test
    void testCardValues() {
        Card aceOfSpades = new Card(CardRank.ACE, CardSuit.SPADES);
        assertEquals(11, aceOfSpades.getCardValue());

        Card kingOfHearts = new Card(CardRank.KING, CardSuit.HEARTS);
        assertEquals(10, kingOfHearts.getCardValue());
    }

    @Test
    void testCardNameQueen() {
        Card card = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        assertTrue(card.getCardName().contains("дама"));
        assertTrue(card.getCardName().contains("Бубновая"));
    }

    @Test
    void testGetCardNameNumberCard() {
        Card card = new Card(CardRank.TEN, CardSuit.DIAMONDS);
        assertTrue(card.getCardName().contains("Десятка"));
        assertTrue(card.getCardName().contains("Бубен"));
    }

    @Test
    void testGetCardNameJack() {
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);
        assertTrue(card.getCardName().contains("валет"));
        assertTrue(card.getCardName().contains("Трефовый"));
    }

    @Test
    void testToString() {
        Card card = new Card(CardRank.SEVEN, CardSuit.CLUBS);
        assertEquals(card.getCardName(), card.toString());
    }
}