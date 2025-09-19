package ru.nsu.ga.grentseva.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void testAddCardAndHandValue() {
        Player player = new Player();
        player.addCard(new Card(CardRank.FIVE, CardSuit.HEARTS));
        player.addCard(new Card(CardRank.SIX, CardSuit.CLUBS));
        assertEquals(11, player.getHandValue());
    }

    @Test
    void testClearHand() {
        Player player = new Player();
        player.addCard(new Card(CardRank.TEN, CardSuit.DIAMONDS));
        player.clearHand();
        assertEquals(0, player.getHandCards().size());
    }
}