package ru.nsu.ga.grentseva.Task_1_1_2;

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