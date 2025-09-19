package ru.nsu.ga.grentseva.task_1_1_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void testBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.addCard(new Card(CardRank.KING, CardSuit.HEARTS));

        assertEquals(21, hand.getHandValue());
        assertTrue(hand.hasBlackjack());
        assertFalse(hand.isBust());
    }

    @Test
    void testBustWithSingleAce() {
        Hand hand = new Hand();
        hand.addCard(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.addCard(new Card(CardRank.KING, CardSuit.HEARTS));
        hand.addCard(new Card(CardRank.TEN, CardSuit.DIAMONDS));
        hand.addCard(new Card(CardRank.TWO, CardSuit.CLUBS));

        assertTrue(hand.isBust());
    }

    @Test
    void testMultipleAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.addCard(new Card(CardRank.ACE, CardSuit.HEARTS));
        hand.addCard(new Card(CardRank.NINE, CardSuit.DIAMONDS));

        assertEquals(21, hand.getHandValue());
        assertFalse(hand.isBust());
    }

    @Test
    void testClearHand() {
        Hand hand = new Hand();
        hand.addCard(new Card(CardRank.TWO, CardSuit.CLUBS));
        hand.addCard(new Card(CardRank.THREE, CardSuit.HEARTS));

        hand.clearHand();
        assertEquals(0, hand.getCards().size());
        assertEquals(0, hand.getHandValue());
    }
}