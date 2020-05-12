package ru.tutorial.exampleProjects.ccg.сcgClient;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class CcgClient extends JFrame implements KeyListener, MouseListener {
    //    static CardView testCard = new CardView(1, 2, 3, "Card",
//            "Strong card with really long long description");
    static PlayerView playerView = new PlayerView("Player 1", 0);
    static EnemyView enemyView = new EnemyView("Enemy 1", 0, 0);
    static boolean waitingForAction = false;
    //    static {
//        playerView.table[3] = testCard;
//        playerView.table[4] = testCard;
//    }



    public static void draw(Graphics2D g) {
        if(waitingForAction){
            g.setColor(new Color(100, 255, 100, 100));
            g.fillRect(0 , 0 , Config.w, Config.h);
        }
        playerView.drawMe(g);
        enemyView.drawMe(g);
    }

    public void mouseClicked(MouseEvent e) {
        int cardInHand = playerView.getCardInHandAt(e.getX(), e.getY());
        if(waitingForAction && cardInHand != -1){
            playerView.selectedCard = cardInHand;
        }
        int slot = playerView.getSlotAt(e.getX(), e.getY());
        if(waitingForAction && slot != -1 && playerView.selectedCard != -1){
            waitingForAction = false;
            toServer.println("play " + playerView.selectedCard + " " + slot);
            playerView.selectedCard = -1;
        }
        System.out.println("Card " + cardInHand + " slot " + slot);
    }


    //Функция вызывается при нажатии клавиши один раз, и при удерживании несколько раз в секунду
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            toServer.println("end");
            waitingForAction = false;
            playerView.selectedCard = -1;
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            Config.cardW *= 0.9;
            Config.cardH *= 0.9;
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            Config.cardW *= 1.1;
            Config.cardH *= 1.1;

        }

    }

    static Scanner fromServer = null;
    static PrintWriter toServer = null;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws Exception {

        Socket server = new Socket("localhost", 1337);
        fromServer = new Scanner(server.getInputStream());
        toServer = new PrintWriter(server.getOutputStream(), true);

        new Thread(()-> {
            while (true){
                ServerMessagesReader.read();
            }
        }).start();

        CcgClient jf = new CcgClient();
        jf.setSize(Config.w, Config.h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        jf.addKeyListener(jf);
        jf.addMouseListener(jf);
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


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
