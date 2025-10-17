package ru.nsu.ga.grentseva.console.localization;

import ru.nsu.ga.grentseva.card.Card;
import java.util.List;

public interface Localization {

    String welcome();

    String playerCards(List<Card> cards, int value);
    String dealerCardsHidden(List<Card> cards);
    String dealerCardsOpen(List<Card> cards, int value);

    String roundResultPlayerWin();
    String roundResultDealerWin();
    String roundResultDraw();
    String roundStart(int roundCounter);

    String dealerBust();
    String playerBust();
    String currentScore(int playerScore, int dealerScore);
    String askPlayerWantsToContinueGame();

    String askPlayerToStopOrTakeCard();
    String invalidInput();

    String playerDraws(Card card);
    String dealerDraws(Card card);
    String dealerReveals(Card card);
    String blackjackPlayer();
    String blackjackDealer();
}
