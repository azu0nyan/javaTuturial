/* Максимально простой способ запустить нестатичную графику и управление с клавиатуры в своей программе.
 * В угоду простоте ниже используются нелучшие практики программирования
 */
package ru.tutorial.other;//првоерьте что у вас правильно написан пакет(это должен быть путь к этому файлу от корня папки с кодом

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 Класс - окно выводящаеся на экран благодаря extends JFrame
 И обрабатывающее нажатия клавиш благодаря implements KeyListener
 Название класса должно совпадать с именем файла(как и всегда)
 */
public class Graphics extends JFrame implements KeyListener {
    //Тут храним всякие данные
    static int x = 700;
    static int y = 700;

    static int x1Speed = 10;
    static int x1 = 100;
    static int y1 = 700;


    /**
     * Все что должно рисоваться в вашей программе рисуется тут
     * Функция рисует один кадр,вызывается в бесконечном цикле, не чаще 60 раз в секунду.
     * Тут не должно быть никаких длительных операций, огромных циклов, ожидания, чтения пользовательского ввода*/
    public static void draw(Graphics2D g) {

        g.setColor(new Color(162, 92, 67));
        g.setFont(new Font("Calibri", Font.BOLD, 25));
        g.drawString("TEXT", 100, 100);//пишем текст


        g.setStroke(new BasicStroke(4));//ширина линии
        //верхний левый угол, ширина, высота
        g.drawRect(100, 100, 300, 500);
        g.setColor(Color.RED);
        g.fillOval(100, 100, 300, 500);

        g.drawLine(400, 600, 500, 100);

        /////
        g.drawOval(x - 5, y - 5, 10, 10);

        g.setColor(Color.GREEN);
        x1 = x1 + x1Speed;
        if (x1 > w) {
            x1Speed = -10;
        } else if (x1 < 0) {
            x1Speed = 10;
        }
        g.drawOval(x1 - 5, y1 - 5, 10, 10);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            y = y - 50;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            y = y + 50;
        }
    }

    static final int w = 1920;
    static final int h = 1080;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        Graphics jf = new Graphics();
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

    public void keyReleased(KeyEvent e) {

    }
}
