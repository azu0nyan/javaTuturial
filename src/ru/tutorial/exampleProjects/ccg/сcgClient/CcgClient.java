package ru.tutorial.exampleProjects.ccg.сcgClient;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class CcgClient extends JFrame implements KeyListener {
    static CardView testCard = new CardView(1, 2, 3, "Card",
            "Strong card with realy long long description");
    static PlayerView playerView = new PlayerView("Player 1", 4, new CopyOnWriteArrayList<>());
    static EnemyView enemyView = new EnemyView("Enemy 1", 5, 3);
    static {
        playerView.table[3] = testCard;
        playerView.table[4] = testCard;
    }

    public static void draw(Graphics2D g) {

        playerView.drawMe(g);
        enemyView.drawMe(g);
    }

    //Функция вызывается при нажатии клавиши один раз, и при удерживании несколько раз в секунду
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            Config.cardW *= 0.9;
            Config.cardH *= 0.9;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            Config.cardW *= 1.1;
            Config.cardH *= 1.1;

        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            enemyView.hand +=1;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            playerView.hand.add(new CardView(
                    (int) Math.random() * 5,
                    (int) Math.random() * 5,
                    (int) Math.random() * 5,
                    "Card " + (int) Math.random() * 5,
                    "Some default card decription"
            ));
        }
    }


    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        CcgClient jf = new CcgClient();
        jf.setSize(Config.w, Config.h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        jf.addKeyListener(jf);
        //в бесконечном цикле рисуем новый кадр
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета  60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}
