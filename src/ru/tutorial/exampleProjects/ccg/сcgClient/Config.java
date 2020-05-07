package ru.tutorial.exampleProjects.ccg.сcgClient;

import ru.tutorial.exampleProjects.ccg.Player;

import java.awt.*;

public class Config {
    static final int w = 1366;
    static final int h = 768;


    static int cardW = 140;
    static int cardH = 200;
    static final int border = 10;
    static final int circleSize = cardW / 5;

    static final int tableY = 100;
    static final int tableX = w / 2 - (cardW + border) * Player.tableSize / 2;
    static final int playerTableY = tableY + cardH + border;

    static final int playerDeckX =  w / 2 + (cardW + border) * (Player.tableSize ) / 2 + border * 4 ;
    static final int playerDeckY =  playerTableY;

    static final int enemyDeckX =  w / 2 - (cardW + border) * (Player.tableSize ) / 2 - border * 4 - cardW;
    static final int enemyDeckY =  tableY;

    static final int playerHandY =  playerTableY + cardH + border;
    static final int playerHandX =  w / 6;
    static final int playerHandW =  w * 3 / 6;

    static final int enemyHandY =  - cardH / 2;
    static final int enemyHandX =  playerHandX;
    static final int enemyHandW =  playerHandW;

    static final int playerHpX = playerHandX - cardW;
    static final int playerHpY = playerHandY;

    static final int enemyHpX = enemyHandX + (cardW + border) * Player.tableSize  + cardW;
    static final int enemyHpY = enemyHandY + cardH * 3 / 2;

    static final Color cardBG = new Color(85, 46, 27);
    static final Color hpBG = new Color(159, 246, 96);
    static final Color atkBG = new Color(246, 96, 96);
    static final Color manaBG = new Color(162, 225, 255);
    static final Color cardDescr = new Color(246, 223, 96);
    static final Color cardText = new Color(0, 0, 0);


}
