package ru.nsu.ga.grentseva.console;

import java.util.Scanner;

public class ConsoleInput {
    private final Scanner in = new Scanner(System.in);
    private boolean useEnglish = false; // флаг языка

    public void setUseEnglish(boolean useEnglish) {
        this.useEnglish = useEnglish;
    }

    private String msg(Message message) {
        return useEnglish ? message.formatEn() : message.format();
    }

    private String msg(Message message, Object... args) {
        return useEnglish ? message.formatEn(args) : message.format(args);
    }

    public int askPlayerChoice() {
        System.out.println(msg(Message.ASK_PLAYER_CHOICE));
        return in.nextInt();
    }

    public boolean askContinue() {
        System.out.println(msg(Message.ASK_CONTINUE));
        while (true) {
            int choice = in.nextInt();
            if (choice == 1) {
                return true;
            }
            if (choice == 0) {
                in.close();
                return false;
            }
            System.out.println(msg(Message.INVALID_INPUT));
        }
    }

    public int nextInt() {
        return in.nextInt();
    }
}
