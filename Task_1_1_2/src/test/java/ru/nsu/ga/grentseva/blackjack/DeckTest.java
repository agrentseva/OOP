package ru.nsu.ga.grentseva.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckSize() {
        Deck deck = new Deck(1);
        assertEquals(52, deck.getDeck().size());
    }

    @Test
    void testTakeCard() {
        Deck deck = new Deck(1);
        Card firstCard = deck.take();
        assertNotNull(firstCard);
        assertEquals(51, deck.getDeck().size());
    }
}