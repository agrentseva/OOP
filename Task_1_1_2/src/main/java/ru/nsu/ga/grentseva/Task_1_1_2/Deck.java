package ru.nsu.ga.grentseva.Task_1_1_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> deck = new ArrayList<>();

    public Deck(int numDeck) {
        for (int n = 0; n < numDeck; n++) {
            for (CardSuit suit : CardSuit.values()) {
                for (CardRank rank : CardRank.values()) {
                    deck.add(new Card(rank, suit));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card take() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        }
        return null;
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

}
