package ru.tutorial.other;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class ForbidKeys extends JFrame implements KeyListener {
    public static void draw(Graphics2D g) {

    }

    public void trueKeyPressed(KeyEvent e){
        System.out.println(e);
    }

    static interface KeyFilter{
        boolean allow(KeyEvent e);
    }

//    CopyOnWriteArrayList<Function<KeyEvent, Boolean>> filters = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<KeyFilter> filters = new CopyOnWriteArrayList<>();
    public void keyPressed(KeyEvent e) {
        if(filters.stream().allMatch(fi -> fi.allow(e))) trueKeyPressed(e);
    }

    static final int w = 1920;
    static final int h = 1080;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        KeyFilter forbidA = new KeyFilter() {
            public boolean allow(KeyEvent e) {
                return e.getKeyCode() != KeyEvent.VK_A;
            }
        };
        filters.add(forbidA);
        KeyFilter forbidB = e -> e.getKeyCode() != KeyEvent.VK_B;
        filters.add(forbidB);
        filters.add(e -> e.getKeyCode() != KeyEvent.VK_C);

        new Thread(()->{
            try {
                Thread.sleep(30 * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            filters.remove(forbidA);
        }).start();


        ForbidKeys jf = new ForbidKeys();
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
