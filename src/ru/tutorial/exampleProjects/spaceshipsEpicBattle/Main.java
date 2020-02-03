/* Максимально простой способ запустить нестатичную графику и управление с клавиатуры в своей программе.
 * В угоду простоте ниже используются нелучшие практики программирования
 */
package ru.tutorial.exampleProjects.spaceshipsEpicBattle;//првоерьте что у вас правильно написан пакет(это должен быть путь к этому файлу от корня папки с кодом

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.security.Key;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static ru.tutorial.exampleProjects.spaceshipsEpicBattle.GameResult.*;
import static ru.tutorial.exampleProjects.spaceshipsEpicBattle.GameState.*;

/**
 * Класс - окно выводящаеся на экран благодаря extends JFrame
 * И обрабатывающее нажатия клавиш благодаря implements KeyListener
 * Название класса должно совпадать с именем файла(как и всегда)
 */
public class Main extends JFrame implements KeyListener {
    static GameState state = MENU;
    static GameResult result = UDIFINED;
    static CopyOnWriteArrayList<DrawableUpdatable> objects = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Wall> walls = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();

    static double bulletVelocity = 500;
    static double dt = 1d / 60d;
    static V2 grav = new V2(0, 100);

    static SpaceShip player1;
    static SpaceShip player2;

    static void initGame() {
        player1 = new SpaceShip(new V2(300, 500));
        player2 = new SpaceShip(new V2(1500, 500));
        player2.up = KeyEvent.VK_UP;
        player2.left = KeyEvent.VK_LEFT;
        player2.right = KeyEvent.VK_RIGHT;
        player2.shoot = KeyEvent.VK_DOWN;
        player2.sqColor = Color.BLUE;


        new Wall(new V2(0, 300), new V2(50, 1000));
        new Wall(new V2(900, 300), new V2(50, 1000));
        new Wall(new V2(1800, 300), new V2(50, 1000));

    }

    static void gameTick(Graphics2D g) {
        /*CopyOnWriteArrayList<Bullet> left = new CopyOnWriteArrayList<>();
        for (Bullet bullet : bullets) {
            boolean good = true;
            for (Wall wall : walls) {
                if(wall.contains(bullet.pos)){
                    good = false;
                    break;
                }
            }
            if(good){
                left.add(bullet);
            } else {
                objects.remove(bullet);
            }
        }
        bullets = left;*/


        bullets.stream().filter(b -> walls.stream().anyMatch(w -> Utils.rectContainsPoint(w.ltAngle, w.dims, b.pos))).forEach(objects::remove);
        bullets = bullets.stream().filter(b -> walls.stream().noneMatch(w -> Utils.rectContainsPoint(w.ltAngle, w.dims, b.pos))).collect(Collectors.toCollection(CopyOnWriteArrayList::new));

        boolean p1Dead = bullets.stream().anyMatch(b -> Utils.rectContainsPoint(player1.ltAngle(), player1.dims(), b.pos));
        boolean p2Dead = bullets.stream().anyMatch(b -> Utils.rectContainsPoint(player2.ltAngle(), player2.dims(), b.pos));
        if(p1Dead || p2Dead){
            state = GAME_OVER;
            if(p1Dead && p2Dead){
                result = DRAW;
            } else if(p1Dead){
                result = P2WIN;
            } else {
                result = P1WIN;
            }
        }
        for (DrawableUpdatable object : objects) {
            object.drawAndUpdate(g);
        }
    }

    /**
     * Все что должно рисоваться в вашей программе рисуется тут
     * Функция рисует один кадр,вызывается в бесконечном цикле, не чаще 60 раз в секунду.
     * Тут не должно быть никаких длительных операций, огромных циклов, ожидания, чтения пользовательского ввода
     */
    static void draw(Graphics2D g) {
        switch (state) {
            case MENU:
                g.setFont(new Font("", Font.BOLD, 70));
                g.drawString("PRESS <SPACE> TO START", 200, 200);
                break;
            case GAME:
                gameTick(g);
                break;
            case GAME_OVER:
                g.setFont(new Font("", Font.BOLD, 70));
                g.drawString("GAME OVER", 200, 200);
                g.drawString(result.toString(), 400, 600);
                break;
        }
    }

    //Функция вызывается при нажатии клавиши один раз, и при удерживании несколько раз в секунду
    public void keyPressed(KeyEvent e) {
        if (state == MENU && e.getKeyCode() == KeyEvent.VK_SPACE) {
            state = GAME;
            initGame();
        }
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }

    static final int w = 1920;
    static final int h = 1080;
    static Main jf = null;

    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        jf = new Main();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("SPACESHIPS EPIC BATTLE");
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


}