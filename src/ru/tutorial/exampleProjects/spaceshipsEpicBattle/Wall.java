package ru.tutorial.exampleProjects.spaceshipsEpicBattle;

import ru.tutorial.exampleProjects.spaceshipsEpicBattle.DrawableUpdatable;

import java.awt.*;

import static ru.tutorial.exampleProjects.spaceshipsEpicBattle.Main.*;

public class Wall implements DrawableUpdatable {

    public Wall(V2 ltAngle, V2 dims) {
        this.ltAngle = ltAngle;
        this.dims = dims;
        walls.add(this);
        objects.add(this);
    }

    Color c = Color.BLACK;

    V2 ltAngle;
    V2 dims;

    @Override
    public void drawAndUpdate(Graphics2D g) {
        g.setColor(c);
        g.fillRect(ltAngle.getXInt(), ltAngle.getYInt(), dims.getXInt(), dims.getYInt());
    }


}
