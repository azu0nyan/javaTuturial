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
 * Класс - окно выводящаеся на экран благодаря extends JFrame
 * И обрабатывающее нажатия клавиш благодаря implements KeyListener
 * Название класса должно совпадать с именем файла(как и всегда)
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
     * Тут не должно быть никаких длительных операций, огромных циклов, ожидания, чтения пользовательского ввода
     */
    public static void draw(Graphics2D g) {

        g.setColor(new Color(162, 92, 67));//задаем цвет через RGB
        g.setFont(new Font("Calibri", Font.BOLD, 25));//Задаем шрифт, его свойства и размер
        g.drawString("TEXT", 100, 100);//пишем текст в точке 100 100


        g.setStroke(new BasicStroke(4));//задаем ширину линии которой все ресуется
        //верхний левый угол, ширина, высота
        g.drawRect(100, 100, 300, 500);//Рисуем прямоугольник
        g.setColor(Color.RED);//Задаем цвет из списка цветов
        g.fillOval(100, 100, 300, 500);//Рисуем закрашенный элипс

        g.drawLine(400, 600, 500, 100);//Рисуем линию

        /*Рисуем круг 10х10 с центром в точке x, y
        Т.к. круг вписывается в квадрат размера 10х10 с левым верхним уголом в точке x - 5, y - 5*/
        g.drawOval(x - 5, y - 5, 10, 10);

        g.setColor(Color.GREEN);
        //если в этот кадр мы вылетим за край экрана летим в другую сторону
        if (x1 + x1Speed > w) {
            x1Speed = -10;
        } else if (x1 + x1Speed < 0) {
            x1Speed = 10;
        }
        //Передвигаем зеленый шарик
        x1 = x1 + x1Speed;
        g.drawOval(x1 - 5, y1 - 5, 10, 10);
    }

    //Функция вызывается при нажатии клавиши один раз, и при удерживании несколько раз в секунду
    public void keyPressed(KeyEvent e) {
        //Вариант 1 (управляем красным шариком)
        //Если клавиша "W" нажата летим красным шариком вверх
        if (e.getKeyCode() == KeyEvent.VK_W) {
            y = y - 50;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            y = y + 50;
        }
        //Вариант 2(управляем зеленым шариком), удобен для назначения нескольких клавиш на одно действие
        switch (e.getKeyCode()) {
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_I:
            case KeyEvent.VK_UP:
                y1 -= 25;
                break;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_O:
            case KeyEvent.VK_DOWN:
                y1 += 25;
                break;
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

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}
