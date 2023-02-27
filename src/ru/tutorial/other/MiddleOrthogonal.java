package ru.tutorial.other;


import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс - окно выводящаеся на экран благодаря extends JFrame
 * И обрабатывающее нажатия клавиш благодаря implements KeyListener
 * Название класса должно совпадать с именем файла(как и всегда)
 */
public class MiddleOrthogonal extends JFrame implements KeyListener, MouseInputListener {
    //Тут храним всякие данные
    static List<V2> clicks = new CopyOnWriteArrayList<>();
    static int dotSize = 10;
    static int lineWidth = 2;


    /**
     * Все что должно рисоваться в вашей программе рисуется тут
     * Функция рисует один кадр,вызывается в бесконечном цикле, не чаще 60 раз в секунду.
     * Тут не должно быть никаких длительных операций, огромных циклов, ожидания, чтения пользовательского ввода
     */
    public static void draw(Graphics2D g) {


        if (clicks.size() >= 2) {
            V2 f = clicks.get(clicks.size() - 2);
            V2 s = clicks.get(clicks.size() - 1);
            g.setStroke(new BasicStroke(lineWidth));
            g.setColor(Color.GRAY);
            g.drawLine(f.getXInt(), f.getYInt(), s.getXInt(), s.getYInt());

            V2 center = f.add(s).scale(0.5);
            g.setColor(Color.RED);
            g.fillOval(center.getXInt() - dotSize / 2, center.getYInt() - dotSize / 2, dotSize, dotSize);
            //(x, y) -> (-y, x)


            //center + ortho * u,  u <- R
            V2 body = s.sub(f);
            V2 ortho = body.rotate90().normalize().scale(w * 2);
            V2 othoEnd = center.add(ortho);
            V2 othoEnd2 = center.add(ortho.opposite());
            g.drawLine(center.getXInt(), center.getYInt(), othoEnd.getXInt(), othoEnd.getYInt());
            g.drawLine(center.getXInt(), center.getYInt(), othoEnd2.getXInt(), othoEnd2.getYInt());

            //биссектриса угла f, center, orthoEnd
            V2 s1 = f.sub(center);
            V2 s2 = othoEnd.sub(center);
            double a = s1.angle(s2);
            V2 bissector = s1.rotate(a / 2d).normalize().scale(w * 2);

            //center + bissector * u,  u <- R
            V2 bissectorEnd = center.add(bissector);
            g.setColor(new Color(255, 0, 0, 127));
            g.drawLine(center.getXInt(), center.getYInt(), bissectorEnd.getXInt(), bissectorEnd.getYInt());

        }

        g.setColor(Color.BLACK);
        for (V2 click : clicks) {
            g.fillOval(click.getXInt() - dotSize / 2, click.getYInt() - dotSize / 2, dotSize, dotSize);
        }
    }


    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            clicks.add(new V2(e.getX(), e.getY()));
            if (clicks.size() > 2) {
                clicks.remove(0);
            }
        }
    }


    static final int w = 1920;
    static final int h = 1080;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        MiddleOrthogonal jf = new MiddleOrthogonal();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        jf.addKeyListener(jf);
        jf.addMouseListener(jf);
        jf.addMouseMotionListener(jf);

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


    //Функция вызывается при нажатии клавиши один раз, и при удерживании несколько раз в секунду
    public void keyPressed(KeyEvent e) {


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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
