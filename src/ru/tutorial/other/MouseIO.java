package ru.tutorial.other;


import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MouseIO extends JFrame implements MouseInputListener {

    static int mouseX = 0;
    static int mouseY = 0;

    static int buttonX = 100;
    static int buttonY = 200;
    static int buttonW = 400;
    static int buttonH = 100;

    static boolean buttonActive = true;

    public static void draw(Graphics2D g) {
        if (buttonActive) {
            g.setColor(new Color(255, 0, 0, 128));
            g.fillRect(buttonX, buttonY, buttonW, buttonH);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3));
            g.drawRect(buttonX, buttonY, buttonW, buttonH);
            g.setColor(Color.BLACK);
            g.setFont(new Font("", Font.BOLD, buttonH / 4));
            g.drawString("CLICK ME!!!!!", buttonX + buttonW / 5, buttonY + buttonH / 2);
        }

        g.setFont(new Font("", Font.ITALIC, 10));
        g.drawString("Some floating text", mouseX, mouseY);

    }

    //кнопка мышти нажата и отпущена
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e);
        if (e.getButton() == MouseEvent.BUTTON3) {//если мы нажали правую кнопку мыши
            try {
                //Перемещаем курсор мыши в случайную часть экрана
                Robot robot = new Robot();
                Random random = new Random();
                robot.mouseMove(random.nextInt(getWidth()), random.nextInt(getHeight()));
            } catch (AWTException ex) {
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            //если мы нажали левую кнопку мыши, проверяем помали ли мы на кнопку
            if (buttonActive &&
                    e.getX() >= buttonX && e.getX() <= buttonX + buttonW &&
                    e.getY() >= buttonY && e.getY() <= buttonY + buttonH
            ) {
                buttonActive = false;
            }
        }
        //если нажали на колесико, сделать кнопку активной
        if(e.getButton() == MouseEvent.BUTTON2 ){
            buttonActive = true;
        }

    }

    //кнопка мыши нажата
    @Override
    public void mousePressed(MouseEvent e) {

    }

    //кнопка мыши отпущена
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    //курсор зашел на окно
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    //курсор вышл за пределы окна
    @Override
    public void mouseExited(MouseEvent e) {

    }

    //Мышка метеретаскиевает что-то
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    //мышка передвинулась
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

    }


    static final int w = 800;
    static final int h = 600;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        MouseIO jf = new MouseIO();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Моя супер программа");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);

        //!!!!!!!!!!!!!!!
        jf.addMouseListener(jf);//!!!!!!добавляем слушатель к окну
        jf.addMouseMotionListener(jf);///////!!!!!!!!

        //Создаем изображение своего курсора
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        java.awt.Graphics cursorGr = cursorImg.getGraphics();
        //рисуем
        cursorGr.setColor(new Color(23, 13, 214));
        cursorGr.fillOval(0, 0, 16, 16);
        cursorGr.setColor(Color.RED);
        cursorGr.drawLine(8, 0, 8, 16);
        cursorGr.drawLine(0, 8, 16, 8);
        //инициируем курсор
        Cursor myCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "custom cursor");
        // Set cursor to the JFrame.
        jf.getContentPane().setCursor(myCursor);


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


}

