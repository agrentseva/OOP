package ru.nsu.ga.grentseva.console.localization;

import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.card.CardLocalization.*;

import java.util.List;

import static ru.nsu.ga.grentseva.card.Card.getCardName;
import static ru.nsu.ga.grentseva.card.Card.getCards;

public class EnglishLocalization implements Localization {

    @Override
    public String welcome() {
        return "Welcome to Blackjack!";
    }

    @Override
    public String roundStart(int roundCounter) {
        return "\nRound " + roundCounter;
    }

    @Override
    public String playerCards(List<Card> cards, int value) {
        return "Your cards: " + getCards(cards) + " > " + value;
    }

    @Override
    public String dealerCardsHidden(List<Card> cards) {
        return "Dealer's cards: " + getCards(cards, true);
    }

    @Override
    public String dealerCardsOpen(List<Card> cards, int value) {
        return "Dealer's cards: " + getCards(cards, false) + " > " + value + "\n";
    }

    @Override
    public String roundResultPlayerWin() {
        return "You win the round!";
    }

    @Override
    public String roundResultDealerWin() {
        return "Dealer wins the round.";
    }

    @Override
    public String roundResultDraw() {
        return "It's a draw.";
    }

    @Override
    public String dealerBust() {
        return "Dealer busts!";
    }

    @Override
    public String playerBust() {
        return "You bust!";
    }

    @Override
    public String currentScore(int playerScore, int dealerScore) {
        return "Score — You: " + playerScore + ", Dealer: " + dealerScore;
    }

    @Override
    public String askPlayerWantsToContinueGame() {
        return "Enter '1' to play again or '0' to exit.";
    }

    @Override
    public String playerDraws(Card card) {
        return "You draw " + getCardName(card);
    }

    @Override
    public String dealerDraws(Card card) {
        return "Dealer draws " +  getCardName(card);
    }

    @Override
    public String dealerReveals(Card card) {
        return "Dealer reveals hidden card " +  getCardName(card);
    }

    @Override
    public String blackjackPlayer() {
        return "BLACKJACK! You win!";
    }

    @Override
    public String blackjackDealer() {
        return "Dealer has BLACKJACK!";
    }

    @Override
    public String askPlayerToStopOrTakeCard() {
        return "Enter '1' to take another card or '0' to stand.";
    }

    @Override
    public String invalidInput() {
        return "Invalid input. Please try again.";
    }

}
