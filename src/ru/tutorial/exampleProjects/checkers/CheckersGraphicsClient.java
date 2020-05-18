package ru.tutorial.exampleProjects.checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CheckersGraphicsClient extends JFrame implements KeyListener, MouseListener {
    static char[][] field = new char[CheckersGame.side][CheckersGame.side];
    static int fx = 100, fy = 100;
    static int cellSide = 50;
    static boolean waitingForMove = false;
    static String message = "Waiting game to start...";

    public static void draw(Graphics2D g) {
        g.setFont(new Font("", Font.BOLD, cellSide / 2));
        g.drawString(message, fx, fy - cellSide / 2);
        g.drawString(from, fx + CheckersGame.side * cellSide + 20, fy - cellSide / 2);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(255, 208, 101));
                } else {
                    g.setColor(new Color(167, 167, 167));
                }
                g.fillRect(fx + i * cellSide, fy + j * cellSide, cellSide, cellSide);

                switch (field[j][i]) {//!!!!!!!!!!!!!!
                    case '●':
                        g.setColor(new Color(0, 0, 0));
                        g.fillOval(fx + i * cellSide, fy + j * cellSide, cellSide, cellSide);
                        break;
                    case '⚉':
                        g.setColor(new Color(0, 0, 0));
                        g.fillOval(fx + i * cellSide, fy + j * cellSide, cellSide, cellSide);
                        g.setColor(new Color(141, 141, 141));
                        g.fillOval(fx + i * cellSide + cellSide / 4, fy + j * cellSide + cellSide / 4,
                                cellSide / 2, cellSide / 2);
                        break;
                    case '○':
                        g.setColor(new Color(255, 255, 255));
                        g.fillOval(fx + i * cellSide, fy + j * cellSide, cellSide, cellSide);
                        break;
                    case '⚇':
                        g.setColor(new Color(255, 255, 255));
                        g.fillOval(fx + i * cellSide, fy + j * cellSide, cellSide, cellSide);
                        g.setColor(new Color(118, 118, 118));
                        g.fillOval(fx + i * cellSide + cellSide / 4, fy + j * cellSide + cellSide / 4,
                                cellSide / 2, cellSide / 2);
                        break;

                }
            }
        }
    }

    static String from = "";

    public void mouseClicked(MouseEvent e) {
        if (waitingForMove) {
            int cx = (e.getX() - fx) / cellSide;
            int cy = (e.getY() - fy) / cellSide;
            System.out.println(String.valueOf(cy + 1) + (char) (cx + 'a'));
            if (cx < 0 || cy < 0 || cx >= CheckersGame.side || cy >= CheckersGame.side) {
                System.out.println("Wrong place");
                return;
            }
            if ("".equals(from)) {
                from = String.valueOf(cy + 1) + (char) (cx + 'a');
            } else {//2e 3b
                String to = String.valueOf(cy + 1) + (char) (cx + 'a');
                System.out.printf("turn:" + from + " " + to);
                toServer.println(from);
                toServer.println(to);
                from = "";
                waitingForMove = false;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    static final int w = 800;
    static final int h = 600;


    static PrintWriter toServer = null;
    static Scanner fromServer = null;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws Exception {
        Socket server = new Socket("localhost", 1337);
        fromServer = new Scanner(server.getInputStream());
        toServer = new PrintWriter(server.getOutputStream(), true);

        new Thread(() -> {
            String command;
            while (!(command = fromServer.nextLine()).equals("mend")) {

                if (command.startsWith("a")) {
                    from = "";
                    waitingForMove = true;
                    message = command.substring(1);
//                    String from  = keyboard.next();
//                    String to  = keyboard.next();
//                    toServer.println(from);
//                    toServer.println(to);
                } else if (command.startsWith("b")) {
                    String board = command.substring(1);
                    int line = 0;
                    for (int i = 0; i < board.length(); i += CheckersGame.side * 2 + 4) {
                        String cur = board
                                .substring(i + 4, i + CheckersGame.side * 2 + 4)
                                .replaceAll("\\|", "")
                                .replaceAll("■", " ");
                        System.out.println(cur);
                        if (i == 0) continue;
                        field[line] = cur.toCharArray();
                        line++;
                    }


                } else {
                    message = command.substring(1);
                }
            }
            message = "END";
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }).start();


        CheckersGraphicsClient jf = new CheckersGraphicsClient();
        jf.setSize(w, h);//размер экрана
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
