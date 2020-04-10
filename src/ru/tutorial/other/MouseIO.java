package ru.tutorial.other;


import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MouseIO extends JFrame implements MouseInputListener {

    static int mouseX = 0;
    static int mouseY = 0;

    public static void draw(Graphics2D g) {
        g.drawString("Some floating text", mouseX, mouseY);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e);
        if(e.getButton() == MouseEvent.BUTTON1) {
            try {
                Robot robot = new Robot();
                Random random = new Random();
                robot.mouseMove(random.nextInt(getWidth()), random.nextInt(getHeight()));
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        }

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

        //custom cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics cursorGr = cursorImg.getGraphics();
        cursorGr.setColor(new Color(23, 13, 214));
        cursorGr.fillOval(0, 0, 16, 16);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "custom cursor");
        // Set cursor to the JFrame.
        jf.getContentPane().setCursor(blankCursor);


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

