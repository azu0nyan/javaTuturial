package ru.tutorial.exampleProjects.spaceshipsEpicBattle;

import java.awt.*;

import static ru.tutorial.exampleProjects.spaceshipsEpicBattle.Main.*;

public class Bullet implements DrawableUpdatable{
    V2 pos;
    V2 speed;

    public Bullet(V2 pos, V2 speed) {
        this.pos = pos;
        this.speed = speed;
        objects.add(this);
        bullets.add(this);
    }

    @Override
    public void drawAndUpdate(Graphics2D g) {
        speed = speed.add(grav.scale( dt));
        pos = pos.add(speed.scale( dt));

        g.setColor(Color.RED);
        g.fillOval(pos.getXInt() - 5, pos.getYInt() -5, 10, 10);
    }
}
