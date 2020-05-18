package ru.tutorial.exampleProjects.ccg.сcgClient;

import ru.tutorial.exampleProjects.ccg.Card;
import ru.tutorial.exampleProjects.ccg.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ru.tutorial.exampleProjects.ccg.сcgClient.CcgClient.*;

public class ServerMessagesReader {
    static void read() {
        //playerHand N card1 card2 .. cardN
        //playerTable null|card card1 null|card card2 .. null|card card6
        //enemyTable null|card card1 null|card card2 .. null|card card6
        //player hp mana decksize
        //enemy hp mana deck hand
        //card
        //n~a~m~e~d~e~s~c~r hp mana atk
        //act
        switch (fromServer.next()) {
            case "end":
                System.exit(0);
            case "playerHand":
                playerView.currentHand = readPlayerHand();
                break;
            case "playerTable":
                playerView.table = readTable();
                break;
            case "enemyTable":
                enemyView.table = readTable();
                break;
            case "player":
                playerView.hp = fromServer.nextInt();
                playerView.mana = fromServer.nextInt();
                playerView.deckCardCount = fromServer.nextInt();
                break;
            case "enemy":
                enemyView.hp = fromServer.nextInt();
                enemyView.mana = fromServer.nextInt();
                enemyView.deckCardCount = fromServer.nextInt();
                enemyView.hand = fromServer.nextInt();
                break;
            case "act":
                waitingForAction = true;

        }


    }

    static CardView[] readTable() {
        CardView[] table = new CardView[Player.tableSize];
        for (int i = 0; i < table.length; i++) {
            String c = fromServer.next();
            if (c.equals("card")) {
                table[i] = readCard();
            }
        }
        return table;
    }

    static List<CardView> readPlayerHand() {
        ArrayList<CardView> res = new ArrayList<>();
        int n = fromServer.nextInt();
        for (int i = 0; i < n; i++) {
            res.add(readCard());
        }
        return res;
    }

    static CardView readCard() {
        String name = fromServer.next().replaceAll("~", " ");
        String descr = fromServer.next().replaceAll("~", " ");
        int hp = fromServer.nextInt();
        int mana = fromServer.nextInt();
        int atk = fromServer.nextInt();
        return new CardView(atk, hp, mana, name, descr);
    }
}
