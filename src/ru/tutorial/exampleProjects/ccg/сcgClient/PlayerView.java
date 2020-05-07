package ru.tutorial.exampleProjects.ccg.сcgClient;

import ru.tutorial.exampleProjects.ccg.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.tutorial.exampleProjects.ccg.сcgClient.Config.*;

public class PlayerView {
    String name;
    int hp;
    int deckCardCount;
    CopyOnWriteArrayList<CardView> hand;
    CardView[] table = new CardView[Player.tableSize];

    public PlayerView(String name, int deckCardCount, CopyOnWriteArrayList<CardView> hand) {
        this.name = name;
        this.deckCardCount = deckCardCount;
        this.hand = hand;
    }

    public void drawMe(Graphics2D g) {
        for (int i = 0; i < table.length; i++) {
            int cardX = tableX + i * (cardW + border);
            int cardY = playerTableY;
            if (table[i] != null) {
                table[i].drawMe(g, cardX, cardY);
            } else {
                CardView.drawEmptySlot(g, cardX, cardY);
            }
        }

        int dx = playerDeckX;
        int dy = playerDeckY;
        for (int i = 0; i < deckCardCount; i++) {
            CardView.drawCardBg(g, dx, dy);
            dx += border;
            dy += border;
        }
        if (!hand.isEmpty()) {
            int handSize = hand.size();
            int cardSpace = playerHandW / handSize;

            int hx = playerHandX;
            int hy = playerHandY;

            for (CardView card : hand) {
                card.drawMe(g, hx, hy);
                hx += cardSpace;
            }
        }
        g.setFont(new Font("", Font.BOLD, cardW / 2));
        g.setColor(hpBG);
        g.drawString(String.valueOf(hp), playerHpX, playerHpY);
    }


}
