package ru.nsu.ga.grentseva.blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testAddCardAndHandValue() {
        player.addCard(new Card(CardRank.FIVE, CardSuit.HEARTS));
        player.addCard(new Card(CardRank.SIX, CardSuit.CLUBS));
        assertEquals(11, player.getHandValue());
    }

    @Test
    void testGetHandValue_EmptyHand() {
        assertEquals(0, player.getHandValue());
    }

    @Test
    void testIsBust_WhenBelow21() {
        player.addCard(new Card(CardRank.TEN, CardSuit.HEARTS));
        player.addCard(new Card(CardRank.FIVE, CardSuit.SPADES));
        assertFalse(player.isBust());
    }

    @Test
    void testIsBust_WhenOver21() {
        player.addCard(new Card(CardRank.TEN, CardSuit.HEARTS));
        player.addCard(new Card(CardRank.TEN, CardSuit.SPADES));
        player.addCard(new Card(CardRank.TWO, CardSuit.HEARTS));

        assertTrue(player.isBust());
    }

    @Test
    void testClearHand() {
        player.addCard(new Card(CardRank.TEN, CardSuit.DIAMONDS));
        player.clearHand();
        assertEquals(0, player.getHandCards().size());
    }

    @Test
    void testHasBlackjack() {
        player.addCard(new Card(CardRank.ACE, CardSuit.HEARTS));
        player.addCard(new Card(CardRank.TEN, CardSuit.HEARTS));

        assertTrue(player.hasBlackjack());

        player.addCard(new Card(CardRank.TEN, CardSuit.CLUBS));
        assertFalse(player.hasBlackjack());

    }
}