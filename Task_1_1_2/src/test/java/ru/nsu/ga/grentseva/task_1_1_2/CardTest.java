package ru.nsu.ga.grentseva.task_1_1_2;

import org.junit.jupiter.api.Test;

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
    void testCardName() {
        Card queenOfDiamonds = new Card(CardRank.QUEEN, CardSuit.DIAMONDS);
        assertTrue(queenOfDiamonds.getCardName().contains("дама"));
    }
}