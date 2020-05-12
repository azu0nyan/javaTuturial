package ru.tutorial.exampleProjects.ccg.сcgClient;

import ru.tutorial.exampleProjects.ccg.Player;

import java.awt.*;
import java.util.ArrayList;

import static ru.tutorial.exampleProjects.ccg.сcgClient.Config.*;

public class EnemyView {
    String name;
    int deckCardCount;
    int hand ;
    int hp;
    int mana;
    CardView [] table = new CardView [Player.tableSize];

    public EnemyView(String name, int deckCardCount, int hand) {
        this.name = name;
        this.deckCardCount = deckCardCount;
        this.hand = hand;
    }


    public void drawMe(Graphics2D g) {
        for (int i = 0; i < table.length; i++) {
            int cardX = tableX + i * (cardW + border);
            int cardY = tableY;
            CardView cur = table[i];
            if (cur != null) {
                cur.drawMe(g, cardX, cardY, false);
            } else {
                CardView.drawEmptySlot(g, cardX, cardY);
            }
        }

        int dx = enemyDeckX;
        int dy = enemyDeckY;
        for (int i = 0; i < deckCardCount; i++) {
            CardView.drawCardBg(g, dx, dy);
            dx += border;
            dy += border;
        }
        if (hand != 0) {
            int cardSpace = enemyHandW / hand;

            int hx = enemyHandX;
            int hy = enemyHandY;

            for (int i = 0; i < hand; i++) {
                CardView.drawCardBg(g, hx, hy);
                hx += cardSpace;
            }
        }

        g.setFont(new Font("", Font.BOLD, cardW / 2));
        g.setColor(hpBG);
        g.drawString(hp + " " + mana, enemyHpX, enemyHpY);

    }
}
