package ru.nsu.ga.grentseva.console;

import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.console.localization.Localization;
import ru.nsu.ga.grentseva.console.localization.RussianLocalization;

import java.util.List;

public class ConsoleOutput {
    private final Localization localization;

    public ConsoleOutput(Localization localization) {
        this.localization = localization;
    }

    public void printWelcome() {
        System.out.println(localization.welcome());
    }

    public void printRound(int roundCounter) {
        System.out.println(localization.roundStart(roundCounter));
    }

    public void printPlayerCards(List<Card> cards, int value) {
        System.out.println(localization.playerCards(cards, value));
    }

    public void printDealerCardsHidden(List<Card> cards) {
        System.out.println(localization.dealerCardsHidden(cards));
    }

    public void printDealerCardsOpen(List<Card> cards, int value) {
        System.out.println(localization.dealerCardsOpen(cards, value));
    }

    public void printPlayerWin() {
        System.out.println(localization.roundResultPlayerWin());
    }

    public void printDealerWin() {
        System.out.println(localization.roundResultDealerWin());
    }

    public void printDraw() {
        System.out.println(localization.roundResultDraw());
    }

    public void printDealerBust() {
        System.out.println(localization.dealerBust());
    }

    public void printPlayerBust() {
        System.out.println(localization.playerBust());
    }

    public void printScore(int playerScore, int dealerScore) {
        System.out.println(localization.currentScore(playerScore, dealerScore));
    }

    public void printAskContinue() {
        System.out.println(localization.askContinue());
    }

    public void printPlayerDraws(Card card) {
        System.out.println(localization.playerDraws(card));
    }

    public void printDealerDraws(Card card) {
        System.out.println(localization.dealerDraws(card));
    }

    public void printDealerReveals(Card card) {
        System.out.println(localization.dealerReveals(card));
    }

    public void printBlackjackPlayer() {
        System.out.println(localization.blackjackPlayer());
    }

    public void printBlackjackDealer() {
        System.out.println(localization.blackjackDealer());
    }

    public void printInvalidValue() {
        System.out.println(localization.invalidInput());
    }

    public void printAskPlayer() {
        System.out.println(localization.askPlayerChoice());
    }

}
