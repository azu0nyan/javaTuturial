package ru.tutorial.other;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class SimplePathfinding extends JFrame implements KeyListener, MouseListener {
    static final int w = 1366;
    static final int h = 768;
    //Сетка представляется в программе в виде двумерного массива, ячеек grid (в нашем случае в ячейках будут числа)
    static final int cellsX = (w - 200) / 20;//количество ячеек по оси х и у
    static final int cellsY = (h - 200) / 20;
    static final int[][] grid = new int[cellsX][cellsY];

    static {//заполняем сетку случайными часламми  от 0 до 2
        Random r = new Random();
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                grid[i][j] = r.nextDouble() > 0.2 ? 0 : -1;
            }
        }
    }

    static final int GRASS = 0; //пусть 0 кодирует клетку с травой
    static final int WALL = -1; //пусть 1 кодирует клетку с землей


    static final int gridX = 100;//координаты левого верхнего угла сетки
    static final int gridY = 100;
    static final int gridW = w - 200;//Шририна и высота сетки
    static final int gridH = h - 200;
    static final int cellSizeX = gridW / cellsX;// длинна стороны ячейки
    static final int cellSizeY = gridH / cellsY;
    static int frames = 0;


    public static void draw(Graphics2D g) {
        frames++;
        if(frames % 4== 0){
            moveBot();
        }
        //чтобы нарисовать сетку нужно перебрать все её клетки
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                //и для каждой клетки нарисовать прямоугольник нужного цвета, проще всего это сделать через switch
                switch (grid[i][j]) {
                    case GRASS:
                        g.setColor(Color.GREEN);
                        break;
                    case WALL:
                        g.setColor(new Color(0, 9, 29));
                        break;
                    default:
                        g.setColor(new Color(0, 0, 0, 0));
                        //неправильное число в сетке
                }

                g.fillRect(gridX + cellSizeX * i, gridY + cellSizeY * j, cellSizeX, cellSizeY);
                g.setColor(Color.BLACK);
                g.drawString(grid[i][j] +"", gridX + cellSizeX * i, gridY + cellSizeY * (j +1));
            }
        }
        //рисуем игрока поверх сетки
        g.setColor(new Color(0, 0, 0, 0.3f));//полупрозрачный круг-тень под игроком
        g.fillOval(gridX + cellSizeX * botX, gridY + (cellSizeY) * botY, cellSizeX, cellSizeY);
        g.setFont(new Font("", Font.BOLD, cellSizeY));
        g.setColor(Color.RED);
        // + cellSizeY смещение вниз, т.к. тест пишется из нижней левой точки, а клетка рисуется из верхней левой
        g.drawString("@", gridX + cellSizeX * botX, gridY + cellSizeY * botY + cellSizeY * 3 / 4);

    }

    //давайте добавим игрока
    static int botX = cellsX / 2;//положение игрока на сетке
    static int botY = cellsY / 2;
    static int botTX = cellsX / 2;//положение игрока на сетке
    static int botTY = cellsY / 2;


    public static void recalculatePaths(int tx, int ty) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] >= 0) grid[i][j] = 0;
            }
        }
        grid[tx][ty] = 1;
        int cur = 1;

        boolean overwritten = false;
        do {
            overwritten = false;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] != 0) continue;
                    if ((i - 1 >= 0 && grid[i - 1][j] == cur) ||
                            (j - 1 >= 0 && grid[i][j - 1] == cur) ||
                            (i + 1 < cellsX && grid[i + 1][j] == cur) ||
                            (j + 1 < cellsY && grid[i][j + 1] == cur)) {
                        grid[i][j] = cur + 1;
                        overwritten = true;
                    }
                }
            }
            cur++;
        } while (overwritten);

    }
    public static void moveBot(){
        if(grid[botX][botY] > 0){
            if ((botX - 1 >= 0 && grid[botX - 1][botY] == grid[botX][botY] -1) ){
                botX--;
            } else if(botY - 1 >= 0 && grid[botX][botY - 1]  == grid[botX][botY] -1){
                botY--;
            } else if((botX + 1 < cellsX && grid[botX + 1][botY]  == grid[botX][botY] -1)) {
                botX++;
            } else if     (botY + 1 < cellsY && grid[botX][botY + 1]  == grid[botX][botY] -1) {
                botY++;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int cx = (e.getX() - gridX) / cellSizeX;
        int cy = (e.getY() - gridY) / cellSizeY;
        if (e.getButton() == MouseEvent.BUTTON1) {
            grid[cx][cy] = -1;
            recalculatePaths(botTX, botTY);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            botX = cx;
            botY = cy;
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            botTX = cx;
            botTY = cy;
            recalculatePaths(botTX, botTY);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }


    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        SimplePathfinding jf = new SimplePathfinding();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Grid");
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

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
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
