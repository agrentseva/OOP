package ru.nsu.ga.grentseva.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.ga.grentseva.card.Card.getCardName;

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
        String nameRu = getCardName(card);
        assertTrue(nameRu.contains("Дама"));
        assertTrue(nameRu.contains("Бубновая"));
    }

    @Test
    void testGetCardNameNumberCardRussian() {
        Card card = new Card(CardRank.TEN, CardSuit.DIAMONDS);
        String nameRu = getCardName(card);
        assertTrue(nameRu.contains("Десятка"));
        assertTrue(nameRu.contains("Бубен"));
    }

    @Test
    void testGetCardNameJackRussian() {
        Card card = new Card(CardRank.JACK, CardSuit.CLUBS);
        String nameRu = getCardName(card);
        assertTrue(nameRu.contains("Валет"));
        assertTrue(nameRu.contains("Трефовый"));
    }

}
