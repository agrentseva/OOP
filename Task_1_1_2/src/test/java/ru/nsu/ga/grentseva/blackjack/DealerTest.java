package ru.nsu.ga.grentseva.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @Test
    void testDealerPlay() {
        Deck deck = new Deck(1);
        Dealer dealer = new Dealer();
        dealer.dealerPlay(deck);
        assertTrue(dealer.getHandValue() >= 17);
    }
}