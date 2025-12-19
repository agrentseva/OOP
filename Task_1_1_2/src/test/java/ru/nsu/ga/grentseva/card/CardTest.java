package ru.nsu.ga.grentseva.card;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.CardLocalization.EnglishCardLocalization;
import ru.nsu.ga.grentseva.card.CardLocalization.RussianCardLocalization;

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
    void testAllRussianCards() {
        Card.setLocalization(new RussianCardLocalization());

        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                Card card = new Card(rank, suit);
                String name = Card.getCardName(card);

                assertTrue(name.contains("(" + rank.getCardValue() + ")"));

                if (rank == CardRank.QUEEN) {
                    assertTrue(name.contains("Дама"));
                } else if (rank == CardRank.JACK) {
                    assertTrue(name.contains("Валет"));
                } else if (rank == CardRank.KING) {
                    assertTrue(name.contains("Король"));
                }
            }
        }
    }


    @Test
    void testAllEnglishCards() {
        Card.setLocalization(new EnglishCardLocalization());

        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                Card card = new Card(rank, suit);
                String name = Card.getCardName(card).toLowerCase();

                assertTrue(name.contains(rank.name().toLowerCase()));
                assertTrue(name.contains(suit.name().toLowerCase()));

                assertTrue(Card.getCardName(card).contains("(" + rank.getCardValue() + ")"));
            }
        }
    }
}
