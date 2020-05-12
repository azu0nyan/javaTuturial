package ru.tutorial.exampleProjects.ccg.сcgClient;

import ru.tutorial.exampleProjects.ccg.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.tutorial.exampleProjects.ccg.сcgClient.Config.*;

public class PlayerView {
    int selectedCard = -1;
    String name;
    int hp;
    int mana;
    int deckCardCount;
    List<CardView> currentHand = new ArrayList<>();
    CardView[] table = new CardView[Player.tableSize];

    public PlayerView(String name, int deckCardCount) {
        this.name = name;
        this.deckCardCount = deckCardCount;
    }

    public void drawMe(Graphics2D g) {
        List<CardView> hand = currentHand;
        for (int i = 0; i < table.length; i++) {
            int cardX = tableX + i * (cardW + border);
            int cardY = playerTableY;
            CardView cur = table[i];
            if (cur != null) {
                cur.drawMe(g, cardX, cardY, false);
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

            for (int i = 0; i < hand.size(); i++) {
                hand.get(i).drawMe(g, hx, hy, i == selectedCard);
                hx += cardSpace;
            }
        }
        g.setFont(new Font("", Font.BOLD, cardW / 2));
        g.setColor(hpBG);
        g.drawString(hp + " " + mana, playerHpX, playerHpY);
    }

    int getCardInHandAt(int x, int y) {
        List<CardView> hand = currentHand;
        if(hand.size() <= 0) return -1;
        //hx  + i * playerHandW / handSize -> hx  + min(cardW, (i+1) * playerHandW / handSize)
        //hy -> hy + cardH
        if (y < playerHandY || y > playerHandY + cardH) return -1;

        int dx = x - playerHandX;
        if (dx < 0 || dx > playerHandW + cardW -playerHandW / hand.size()  ) return -1;
        int cardNum =  dx /(playerHandW / hand.size());
        int cardWidth = dx % (playerHandW / hand.size());
        if(cardWidth > cardW) return -1;
        return Math.min(cardNum, hand.size() - 1);
    }
    int getSlotAt(int x, int y){
        if(y < playerTableY || y > playerTableY + cardH ||
                x < tableX || x > tableX + (table.length)* (cardW + border)) return  -1;
        int dx = x - tableX;
        int slotNum = dx / (cardW + border);
        int slotWidth = dx %(cardW + border);
        if(slotWidth > cardW) return -1;
        return slotNum;
    }


}
