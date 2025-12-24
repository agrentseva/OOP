package ru.nsu.ga.grentseva.players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;


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