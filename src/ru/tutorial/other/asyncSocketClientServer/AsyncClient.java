package ru.tutorial.other.asyncSocketClientServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AsyncClient extends JFrame implements KeyListener {

    static int x1 = 100;
    static int y1 = 300;

    static int x2 = 100;
    static int y2 = 100;



    public static void draw(Graphics2D g) {

        g.setColor(Color.RED);
        g.fillRect(x1, y1, 10, 10);
        g.setColor(Color.GREEN);
        g.fillRect(x2, y2, 10, 10);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                toServer.println("LEFT");
                break;
            case KeyEvent.VK_D:
                toServer.println("RIGHT");
                break;
        }

    }

    static final int w = 800;
    static final int h = 600;

    static Scanner fromServer = null;
    static PrintWriter toServer = null;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws Exception {

        Socket server = new Socket("localhost", 1337);
        fromServer = new Scanner(server.getInputStream());
        toServer = new PrintWriter(server.getOutputStream(), true);

        new Thread(() ->{
            while (true){
                x1 = fromServer.nextInt();
                y1 = fromServer.nextInt();
                x2 = fromServer.nextInt();
                y2 = fromServer.nextInt();
            }
        }).start();


        AsyncClient jf = new AsyncClient();
        jf.setSize(w, h);//размер экрана
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

