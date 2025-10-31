package ru.nsu.ga.grentseva.console;

import ru.nsu.ga.grentseva.card.Card;
import ru.nsu.ga.grentseva.players.Dealer;
import ru.nsu.ga.grentseva.players.Player;

import java.util.List;

public class ConsoleOutput {
    private boolean useEnglish = false; // флаг языка

    public void setUseEnglish(boolean useEnglish) {
        this.useEnglish = useEnglish;
    }

    private String msg(Message message, Object... args) {
        return useEnglish ? message.formatEn(args) : message.format(args);
    }

    private String formatCardList(List<Card> cards) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cards.size(); i++) {
            sb.append(cards.get(i).getCardName(useEnglish));
            if (i < cards.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public void printWelcome() {
        System.out.println(msg(Message.WELCOME));
    }

    public void printRoundStart(int round) {
        System.out.println(msg(Message.ROUND_START, round));
    }

    public void showInitialCards(Player player, Dealer dealer) {
        List<Card> dealerCards = dealer.getHandCards();
        System.out.println(msg(Message.DEALER_DEALS_CARDS));
        System.out.println("    " + msg(Message.PLAYER_CARDS, formatCardList(player.getHandCards()), player.getHandValue()));
        String hidden = useEnglish ? "hidden card" : "закрытая карта";
        System.out.println("    " + msg(Message.DEALER_CARDS,
                "[" + dealerCards.get(0).getCardName(useEnglish) + ", " + hidden + "]"));
    }

    public void printPlayerBlackjack() {
        System.out.println(msg(Message.PLAYER_BLACKJACK));
    }

    public void printPlayerTurn() {
        System.out.println(msg(Message.PLAYER_TURN));
    }

    public void printPlayerCard(Card card, Player player, Dealer dealer) {
        System.out.println(msg(Message.PLAYER_OPENED_CARD, card.getCardName(useEnglish)));
        System.out.println("    " + msg(Message.PLAYER_CARDS, formatCardList(player.getHandCards()), player.getHandValue()));
        String hidden = useEnglish ? "hidden card" : "закрытая карта";
        List<Card> dealerCards = dealer.getHandCards();
        System.out.println("    " + msg(Message.DEALER_CARDS,
                "[" + dealerCards.get(0).getCardName(useEnglish) + ", " + hidden + "]"));
    }

    public void printPlayerBust() {
        System.out.println(msg(Message.PLAYER_BUST));
    }

    public void printDealerTurn(Dealer dealer, Player player) {
        System.out.println(msg(Message.DEALER_TURN));
        System.out.println(msg(Message.DEALER_OPENS_HIDDEN, dealer.getHandCards().get(1).getCardName(useEnglish)));
        System.out.println("    " + msg(Message.PLAYER_CARDS, formatCardList(player.getHandCards()), player.getHandValue()));
        System.out.println("    " + msg(Message.DEALER_CARDS,
                formatCardList(dealer.getHandCards()) + " => " + dealer.getHandValue()));
    }

    public void printDealerCard(Card card, Dealer dealer, Player player) {
        System.out.println(msg(Message.DEALER_OPENS_CARD, card.getCardName(useEnglish)));
        System.out.println("    " + msg(Message.PLAYER_CARDS, formatCardList(player.getHandCards()), player.getHandValue()));
        System.out.println("    " + msg(Message.DEALER_CARDS,
                formatCardList(dealer.getHandCards()) + " => " + dealer.getHandValue()));
    }

    public void printDealerBust() {
        System.out.println(msg(Message.DEALER_BUST));
    }

    public void printPlayerWin() {
        System.out.println(msg(Message.PLAYER_WIN));
    }

    public void printDealerWin() {
        System.out.println(msg(Message.DEALER_WIN));
    }

    public void printDraw() {
        System.out.println(msg(Message.DRAW));
    }

    public void printScore(int playerScore, int dealerScore) {
        System.out.println(msg(Message.SCORE, playerScore, dealerScore));
    }

    public void printInvalidInput() {
        System.out.println(msg(Message.INVALID_INPUT));
    }
}
