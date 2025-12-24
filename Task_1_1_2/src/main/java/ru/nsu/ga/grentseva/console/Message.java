package ru.nsu.ga.grentseva.console;

public enum Message {

    WELCOME("Добро пожаловать в Блэкджек!", "Welcome to Blackjack!"),
    ROUND_START("\nРаунд %d", "\nRound %d"),
    DEALER_DEALS_CARDS("Дилер раздал карты:", "Dealer deals cards:"),
    PLAYER_CARDS("Ваши карты: %s => %d", "Player cards: %s => %d"),
    DEALER_CARDS("Карты дилера: %s", "Dealer cards: %s"),
    PLAYER_BLACKJACK("Блэкджек! Вы выиграли раунд.", "Blackjack! You won the round."),
    PLAYER_TURN("\nВаш ход\n-------", "\nYour turn\n-------"),
    PLAYER_OPENED_CARD("Вы открыли карту: %s", "You opened: %s"),
    PLAYER_BUST("Перебор. Вы проиграли.", "Bust! You lost."),
    DEALER_TURN("\nХод дилера\n-------", "\nDealer's turn\n-------"),
    DEALER_OPENS_HIDDEN("Дилер открывает закрытую карту: %s", "Dealer opens hidden card: %s"),
    DEALER_OPENS_CARD("\nДилер открывает карту: %s", "\nDealer opens: %s"),
    DEALER_BUST("Дилер перебрал. Вы выиграли!", "Dealer bust! You win!"),
    PLAYER_WIN("Вы выиграли раунд!", "You won the round!"),
    DEALER_WIN("Вы проиграли раунд!", "You lost the round!"),
    DRAW("Ничья!", "Draw!"),
    SCORE("Счёт: %d:%d", "Score: %d:%d"),
    INVALID_INPUT("Неверный ввод.", "Invalid input."),
    ASK_PLAYER_CHOICE("Введите 1 - взять карту, 0 - остановиться:", "Enter 1 to take a card, 0 to stop:"),
    ASK_CONTINUE("\nХотите сыграть ещё один раунд? (1 - да, 0 - нет):", "\nDo you want to play another round? (1 - yes, 0 - no):");

    private final String ru;
    private final String en;

    Message(String ru, String en) {
        this.ru = ru;
        this.en = en;
    }

    public String format(Object... args) {
        return String.format(ru, args);
    }

    public String formatEn(Object... args) {
        return String.format(en, args);
    }
}
