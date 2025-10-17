package ru.nsu.ga.grentseva.console.localization;

import ru.nsu.ga.grentseva.card.Card;
import java.util.List;
import ru.nsu.ga.grentseva.card.CardLocalization.*;

import static ru.nsu.ga.grentseva.card.Card.getCardName;
import static ru.nsu.ga.grentseva.card.Card.getCards;


public class RussianLocalization implements Localization {

    @Override
    public String welcome() {
        return "Добро пожаловать в Блэкджек!";
    }

    @Override
    public String roundStart(int roundCounter) {
        return "\nРаунд " + roundCounter;
    }

    @Override
    public String playerCards(List<Card> cards, int value) {
        return "Ваши карты: " + getCards(cards) + " > " + value;
    }

    @Override
    public String dealerCardsHidden(List<Card> cards) {
        return "Карты дилера: " + getCards(cards, true);
    }

    @Override
    public String dealerCardsOpen(List<Card> cards, int value) {
        return "Карты дилера: " + getCards(cards, false) + " > " + value + "\n";
    }

    @Override
    public String roundResultPlayerWin() {
        return "Вы выиграли раунд!";
    }

    @Override
    public String roundResultDealerWin() {
        return "Дилер выиграл раунд.";
    }

    @Override
    public String roundResultDraw() {
        return "Ничья.";
    }

    @Override
    public String dealerBust() {
        return "Дилер перебрал!";
    }

    @Override
    public String playerBust() {
        return "Вы перебрали!";
    }

    @Override
    public String currentScore(int playerScore, int dealerScore) {
        return "Счёт — Вы: " + playerScore + ", Дилер: " + dealerScore;
    }

    @Override
    public String askPlayerWantsToContinueGame() {
        return "Введите “1”, чтобы сыграть ещё, и “0”, чтобы выйти.";
    }

    @Override
    public String playerDraws(Card card) {
        return "Вы открыли карту " +  getCardName(card);
    }

    @Override
    public String dealerDraws(Card card) {
        return "Дилер открывает карту " +  getCardName(card);
    }

    @Override
    public String dealerReveals(Card card) {
        return "Дилер открывает закрытую карту " +  getCardName(card);
    }

    @Override
    public String blackjackPlayer() {
        return "У вас БЛЭКДЖЕК!";
    }

    @Override
    public String blackjackDealer() {
        return "У дилера БЛЭКДЖЕК!";
    }

    @Override
    public String askPlayerToStopOrTakeCard() {
        return "Введите “1”, чтобы взять карту, и “0”, чтобы остановиться.";
    }

    @Override
    public String invalidInput() {
        return "Неверный ввод. Попробуйте ещё раз.";
    }
}
