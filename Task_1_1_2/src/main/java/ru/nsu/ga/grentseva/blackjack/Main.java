package ru.nsu.ga.grentseva.blackjack;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Основной метод запуска игры в Блэкджек.
 * Создаёт игроков, колоду и управляет игровым циклом.
 */
public class Main {
    public static void main() {
        Scanner in = new Scanner(System.in);
        Random random = new Random();

        boolean game = true;
        int roundCounter = 0;

        int playerScore = 0;
        int dealerScore = 0;

        Player player = new Player();
        Dealer dealer = new Dealer();

        System.out.println("Добро пожаловать в Блэкджек!");

        while (game) {
            roundCounter++;
            System.out.println("\nРаунд " + roundCounter);

            int randomNumber = random.nextInt(5) + 1;
            Deck deck = new Deck(randomNumber); // создаём колоду
            deck.shuffle();

            player.clearHand();
            dealer.clearHand();

            player.addCard(deck.take());
            player.addCard(deck.take());
            dealer.addCard(deck.take());
            dealer.addCard(deck.take());

            List<Card> dealerCards = dealer.getHandCards();

            System.out.println("Дилер раздал карты: ");
            System.out.println("    Ваши карты: " + player.getHandCards()
                    + " => " + player.getHandValue());
            System.out.println("    Карты дилера: [" + dealerCards.get(0)
                    + ", <закрытая карта>]");

            if (player.hasBlackjack()) {
                System.out.println("Блэкджек! Вы выиграли раунд.");
                playerScore++;
                System.out.println("Счёт: " + playerScore + ":" + dealerScore);
                continue;
            }

            System.out.println("\nВаш ход");
            System.out.println("-------");
            boolean playerTurn = true;
            boolean playerBust = false;

            while (playerTurn) {
                System.out.println("Введите \"1\", чтобы взять карту, "
                        + "и \"0\", чтобы остановиться");
                int playerChoice = in.nextInt();

                if (playerChoice == 1) {
                    Card newCard = deck.take();
                    player.addCard(newCard);
                    System.out.println("Вы открыли карту: " + newCard);
                    System.out.println("    Ваши карты: " + player.getHandCards()
                            + " => " + player.getHandValue());
                    System.out.println("    Карты дилера: [" + dealerCards.get(0)
                            + ", <закрытая карта>]");

                    if (player.isBust()) {
                        System.out.println("Перебор. Вы проиграли");
                        playerBust = true;
                        dealerScore++;
                        System.out.println("Счёт: " + playerScore + ":" + dealerScore);
                        playerTurn = false;
                    }
                } else if (playerChoice == 0) {
                    playerTurn = false;
                } else {
                    System.out.println("Неверный ввод");
                }
            }

            if (playerBust) {
                System.out.println("\nХотите сыграть ещё один раунд?");
                System.out.println("Введите 1 ~ да, 0 ~ нет:");
                boolean enter = true;
                while (enter) {
                    int choice = in.nextInt();
                    if (choice == 1) {
                        game = true;
                        enter = false;
                    } else if (choice == 0) {
                        game = false;
                        enter = false;
                    } else {
                        System.out.println("Неверный ввод");
                    }
                }
                continue;
            }

            System.out.println("\nХод дилера");
            System.out.println("-------");
            System.out.println("Дилер открывает закрытую карту: "
                    + dealerCards.get(1));

            System.out.println("    Ваши карты: " + player.getHandCards()
                    + " => " + player.getHandValue());
            System.out.println("    Карты дилера: " + dealer.getHandCards()
                    + " => " + dealer.getHandValue());

            dealer.dealerPlay(deck);

            System.out.println("    Ваши карты: " + player.getHandCards()
                    + " => " + player.getHandValue());
            System.out.println("    Карты дилера: " + dealer.getHandCards()
                    +  " => " + dealer.getHandValue());

            if (dealer.isBust()) {
                System.out.println("Дилер перебрал. Вы выиграли!");
                playerScore++;
            } else if (player.getHandValue() > dealer.getHandValue()) {
                System.out.println("Вы выиграли раунд!");
                playerScore++;
            } else if (player.getHandValue() < dealer.getHandValue()) {
                System.out.println("Вы проиграли раунд!");
                dealerScore++;
            } else {
                dealerScore++;
                playerScore++;
                System.out.println("Ничья!");
            }
            System.out.println("Счёт: " + playerScore + ":" + dealerScore);

            System.out.println("\nХотите сыграть ещё один раунд?");
            System.out.println("Введите 1 ~ да, 0 ~ нет:");
            int choice = in.nextInt();
            if (choice == 1) {
                game = true;
            } else {
                game = false;
            }
        }
    }
}