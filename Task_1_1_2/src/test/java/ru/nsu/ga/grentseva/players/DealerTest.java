package ru.nsu.ga.grentseva.players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.CardRank;
import ru.nsu.ga.grentseva.card.CardSuit;
import ru.nsu.ga.grentseva.card.Deck;
import ru.nsu.ga.grentseva.console.ConsoleOutput;

class DealerTest {

    @Test
    void testDealerDraws17() {
        Dealer dealer = new Dealer(new ConsoleOutput());
        Player player = new Player();
        Deck deck = new Deck(1);

        dealer.addCard(new Card(CardRank.TWO, CardSuit.HEARTS));
        dealer.addCard(new Card(CardRank.THREE, CardSuit.CLUBS));
        dealer.dealerTurn(deck, player);
        assertTrue(dealer.getHandValue() >= 17);
    }

    @Test
    void testDealerHas17() {
        Dealer dealer = new Dealer(new ConsoleOutput());
        Player player = new Player();
        Deck deck = new Deck(1);

        dealer.addCard(new Card(CardRank.KING, CardSuit.DIAMONDS));
        dealer.addCard(new Card(CardRank.SEVEN, CardSuit.CLUBS));

        int before = dealer.getHandValue();
        dealer.dealerTurn(deck, player);
        int after = dealer.getHandValue();

        assertEquals(before, after);
    }
}