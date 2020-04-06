package ru.tutorial.other;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 * В играх типа "Шахматы", "Змейка", "Пошаговая РПГ ..." и других часто встречается
 * поле в виде сетки. Сетка имеет свою систему координат,
 * первая: номер столбца по горизонтали
 * втоорая: номер строки по вертикали
 * (0,0)(1,0)(2,0)----(i,0)-----------------> X
 * (0,1)(1,1)....
 * (0,2).....
 * |
 * (0,j)               Cij
 * |
 * \/ Y
 */
public class DrawingGrids extends JFrame implements KeyListener {
    //Приписка static у переменных значит что это глобальная переменная
    //Приписка final у переменных значит что это неизменяемая константа, т.е. такие переменные нельзя именять
    //размер экрана, поменяйте на ваш
    static final int w = 1920;
    static final int h = 1080;
    //Сетка представляется в программе в виде двумерного массива, ячеек grid (в нашем случае в ячейках будут числа)
    static final int cellsX = (w - 200) / 20;//количество ячеек по оси х и у
    static final int cellsY = (h - 200) / 20;
    static final int[][] grid = new int[cellsX][cellsY];

    static {//заполняем сетку случайными часламми  от 0 до 2
        Random r = new Random();
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                grid[i][j] = r.nextInt(4);
            }
        }
    }

    //Такое лучше делать через enum, но для простоты я использую числа
    static final int GRASS = 0; //пусть 0 кодирует клетку с травой
    static final int SAND = 1; //пусть 1 кодирует клетку с землей
    static final int WATER = 2; //пусть 2 кодирует клетку с водой
    static final int ASPHALT = 3; //пусть 2 кодирует клетку со стеной


    static final int gridX = 100;//координаты левого верхнего угла сетки
    static final int gridY = 100;
    static final int gridW = w - 200;//Шририна и высота сетки
    static final int gridH = h - 200;
    static final int cellSizeX = gridW / cellsX;// длинна стороны ячейки
    static final int cellSizeY = gridH / cellsY;


    public static void draw(Graphics2D g) {

        //чтобы нарисовать сетку нужно перебрать все её клетки
        for (int i = 0; i < cellsX; i++) {
            for (int j = 0; j < cellsY; j++) {
                //и для каждой клетки нарисовать прямоугольник нужного цвета, проще всего это сделать через switch
                switch (grid[i][j]) {
                    case GRASS:
                        g.setColor(Color.GREEN);
                        break;
                    case SAND:
                        g.setColor(new Color(228, 209, 29));
                        break;
                    case WATER:
                        g.setColor(Color.BLUE);
                        break;
                    case ASPHALT:
                        g.setColor(new Color(117, 115, 128));
                        break;
                    default:
                        //неправильное число в сетке
                }

                g.fillRect(gridX + cellSizeX * i, gridY + cellSizeY * j, cellSizeX, cellSizeY);
            }
        }
        //рисуем игрока поверх сетки
        g.setColor(new Color(0, 0, 0, 0.3f));//полупрозрачный круг-тень под игроком
        g.fillOval(gridX + cellSizeX * playerX, gridY + (cellSizeY) * playerY, cellSizeX, cellSizeY);
        g.setFont(new Font("", Font.BOLD, cellSizeY));
        g.setColor(Color.RED);
        // + cellSizeY смещение вниз, т.к. тест пишется из нижней левой точки, а клетка рисуется из верхней левой
        g.drawString("@", gridX + cellSizeX * playerX, gridY + cellSizeY * playerY + cellSizeY * 3 /4);

    }

    //давайте добавим игрока
    static int playerX = cellsX / 2;//положение игрока на сетке
    static int playerY = cellsY / 2;

    //функция делает один ход игрока в направлении dx dy
    public void turn(int dx, int dy) {
        if (grid[playerX + dx][playerY + dy] != WATER) {//Запрещаем ходить в воду
            playerX += dx;
            playerY += dy;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        System.out.println(playerX + " " + playerY);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                turn(0, -1);
                break;
            case KeyEvent.VK_S:
                turn(0, 1);
                break;
            case KeyEvent.VK_A:
                turn(-1, 0);
                break;
            case KeyEvent.VK_D:
                turn(1, 0);
                break;
        }
    }


    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        DrawingGrids jf = new DrawingGrids();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("Grid");
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

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
