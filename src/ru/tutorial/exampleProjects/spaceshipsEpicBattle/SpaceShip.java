package ru.tutorial.exampleProjects.spaceshipsEpicBattle;

import ru.tutorial.exampleProjects.spaceshipsEpicBattle.DrawableUpdatable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static ru.tutorial.exampleProjects.spaceshipsEpicBattle.Main.*;

public class SpaceShip implements DrawableUpdatable, KeyListener {
    public SpaceShip(V2 sqPos) {
        this.sqPos = sqPos;
        objects.add(this);
        jf.addKeyListener(this);
    }
    int up =KeyEvent.VK_W;
    int left =KeyEvent.VK_Q;
    int right =KeyEvent.VK_E;
    int shoot =KeyEvent.VK_SPACE;

    V2 sqPos;
     V2 sqV = new V2(0, 0);
     V2 sqA = new V2(0, 0);

     int sqSize = 60;
     Color sqColor = Color.GREEN;


     V2 pointPosDelta = new V2(0, -100);
     double pointRotationSpeed = 0;
     double pointRotation = 0;

     V2 leftPoitDelta = new V2(-20, 20);
     V2 rightPoitDelta = new V2(20, 20);

     V2 dims(){
         return new V2(sqSize, sqSize);
     }

     V2 ltAngle(){
         return  sqPos.sub(dims().scale(0.5d));
     }

    @Override
    public void drawAndUpdate(Graphics2D g) {
        //V = V0 + a * t
        V2 a = grav.add(sqA);
        sqV = sqV.add(a.scale( dt));

        //pos = pos0 + V * t
        sqPos = sqPos.add(sqV.scale( dt));


        double rotAngle = pointRotationSpeed *  dt;
        pointRotation += rotAngle;
//        pointPosDelta = pointPosDelta.rotate(rotAngle);
        V2 p1 = sqPos.add(pointPosDelta.rotate(pointRotation));
        V2 p2 = sqPos.add(leftPoitDelta.rotate(pointRotation));
        V2 p3 = sqPos.add(rightPoitDelta.rotate(pointRotation));

        g.setColor(Color.BLACK);
        g.fillPolygon(
                new int[]{p1.getXInt(), p2.getXInt(), p3.getXInt()},
                new int[]{p1.getYInt(), p2.getYInt(), p3.getYInt()},
                3
        );


        g.setColor(sqColor);
        g.fillRect(sqPos.getXInt() - sqSize / 2, sqPos.getYInt() - sqSize / 2, sqSize, sqSize);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == left){
            pointRotationSpeed = 1;
        } else if(e.getKeyCode() == right){
            pointRotationSpeed = -1;
        } else if(e.getKeyCode() == up){
            sqA = new V2(0, -300);
        } else if(e.getKeyCode() == shoot){
            V2 bulletPos = sqPos.add(pointPosDelta.rotate(pointRotation));
            V2 bulletVel = pointPosDelta.rotate(pointRotation).normalize().scale(bulletVelocity);
            new Bullet(bulletPos, bulletVel);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == left){
            pointRotationSpeed = 0;
        } else if(e.getKeyCode() == right){
            pointRotationSpeed = 0;
        } else if(e.getKeyCode() == up){
            sqA = new V2(0, 0);
        }
    }
}
