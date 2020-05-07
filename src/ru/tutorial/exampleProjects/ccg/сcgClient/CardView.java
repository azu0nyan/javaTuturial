package ru.tutorial.exampleProjects.ccg.сcgClient;

import java.awt.*;
import static ru.tutorial.exampleProjects.ccg.сcgClient.Config.*;
public class CardView {
    int attack;
    int hp;
    int mana;
    String name = "Defult name";
    String description = "Default descr";
//    BufferedImage img;

    static void drawEmptySlot(Graphics2D g, int x, int y){
        g.setColor(cardBG);
        g.drawRoundRect(x, y,cardW, cardH, 5, 5);

    }

    static void drawCardBg(Graphics2D g, int x, int y){
        g.setColor(cardBG);
        g.fillRoundRect(x, y,cardW, cardH, 5, 5);
        g.setColor(cardText);
        g.drawRoundRect(x, y,cardW, cardH, 5, 5);

    }

    void drawMe(Graphics2D g, int x, int y){
        g.setColor(cardBG);
        g.fillRoundRect(x, y,cardW, cardH, 5, 5);

        g.setFont(new Font("", Font.BOLD, Config.cardH / 12));

        g.setColor(hpBG);
        g.fillOval(x + cardW * 1 / 4 - circleSize / 2, y  + circleSize / 2, circleSize, circleSize);
        g.setColor(cardText);
        g.drawString(String.valueOf(hp), x + cardW * 1 / 4 - circleSize / 5, y  + circleSize * 5 / 4 );

        g.setColor(atkBG);
        g.fillOval(x + cardW * 2 / 4 - circleSize / 2, y  + circleSize / 2, circleSize, circleSize);
        g.setColor(cardText);
        g.drawString(String.valueOf(attack), x + cardW * 2 / 4 - circleSize / 5, y  + circleSize * 5 / 4 );

        g.setColor(manaBG);
        g.fillOval(x + cardW * 3 / 4 - circleSize / 2, y  + circleSize / 2, circleSize, circleSize);
        g.setColor(cardText);
        g.drawString(String.valueOf(mana), x + cardW * 3 / 4 - circleSize / 5, y  + circleSize * 5 / 4 );

        g.setFont(new Font("", Font.BOLD, Config.cardH / 12));
        g.setColor(Config.cardText);
        g.drawString(name, x + cardW / 4, y + cardH / 3);


        g.setColor(cardDescr);
        g.fillRoundRect(x + border, y + cardH / 2 + border,
                cardW - 2 * border, cardH /2  - 2 * border, 5, 5);

        Font decrFont = new Font("", Font.PLAIN, cardH / 16);
        g.setFont(decrFont);
        g.setColor(cardText);
        String descrArr [] = description.split(" {1,}");
        int descrWidth = cardW - border * 4;
        int descrX = x + border * 2;
        int lineHeight = g.getFontMetrics().getHeight();
        int descrY = y + cardH / 2 + lineHeight + border;
        String cur = "";
        for (int i = 0; i < descrArr.length; i++) {
            String next = cur + " " +  descrArr[i];
            if(g.getFontMetrics().stringWidth(next) > descrWidth){
                g.drawString(cur, descrX, descrY);
                cur = descrArr[i];
                descrY += lineHeight;
            } else {
                cur = next;
            }
            if(i == descrArr.length - 1){
                g.drawString(cur, descrX, descrY);
            }
        }

//        g.getFontMetrics().stringWidth()

    }

    public CardView(int attack, int hp, int mana, String name, String description) {
        this.attack = attack;
        this.hp = hp;
        this.mana = mana;
        this.name = name;
        this.description = description;
    }
}
