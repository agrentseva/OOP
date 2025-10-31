package ru.nsu.ga.grentseva.card;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.Deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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