package ru.nsu.ga.grentseva.blackjack;

import java.util.List;

public class ConsoleOutput {
    public void printWelcome() {
        System.out.println("Добро пожаловать в Блэкджек!");
    }

    public void printRoundStart(int round) {
        System.out.println("\nРаунд " + round);
    }

    public void showInitialCards(Player player, Dealer dealer) {
        List<Card> dealerCards = dealer.getHandCards();
        System.out.println("Дилер раздал карты:");
        System.out.println("    Ваши карты: " + player.getHandCards()
                + " => " + player.getHandValue());
        System.out.println("    Карты дилера: [" + dealerCards.get(0)
                + ", <закрытая карта>]");
    }

    public void printPlayerBlackjack() {
        System.out.println("Блэкджек! Вы выиграли раунд.");
    }

    public void printPlayerTurn() {
        System.out.println("\nВаш ход\n-------");
    }

    public void printPlayerCard(Card card, Player player, Dealer dealer) {
        System.out.println("Вы открыли карту: " + card);
        List<Card> dealerCards = dealer.getHandCards();
        System.out.println("    Ваши карты: " + player.getHandCards()
                + " => " + player.getHandValue());
        System.out.println("    Карты дилера: [" + dealerCards.get(0)
                + ", <закрытая карта>]");
    }

    public void printPlayerBust() {
        System.out.println("Перебор. Вы проиграли.");
    }

    public void printDealerTurn(Dealer dealer, Player player) {
        System.out.println("\nХод дилера\n-------");
        System.out.println("Дилер открывает закрытую карту: " + dealer.getHandCards().get(1));
        System.out.println("    Ваши карты: " + player.getHandCards()
                + " => " + player.getHandValue());
        System.out.println("    Карты дилера: " + dealer.getHandCards()
                + " => " + dealer.getHandValue());
    }

    public void printDealerCard(Card card, Dealer dealer, Player player) {
        System.out.println("\nДилер открывает карту: " + card);
        System.out.println("    Ваши карты: " + player.getHandCards()
                + " => " + player.getHandValue());
        System.out.println("    Карты дилера: " + dealer.getHandCards()
                + " => " + dealer.getHandValue());
    }

    public void printDealerBust() {
        System.out.println("Дилер перебрал. Вы выиграли!");
    }

    public void printPlayerWin() {
        System.out.println("Вы выиграли раунд!");
    }

    public void printDealerWin() {
        System.out.println("Вы проиграли раунд!");
    }

    public void printDraw() {
        System.out.println("Ничья!");
    }

    public void printScore(int playerScore, int dealerScore) {
        System.out.println("Счёт: " + playerScore + ":" + dealerScore);
    }

    public void printInvalidInput() {
        System.out.println("Неверный ввод.");
    }
}





