package ru.tutorial.exampleProjects.spaceshipsEpicBattle;

public class Utils {
    static boolean rectContainsPoint(V2 ltAngle, V2 dims, V2 pos){
        return pos.getX() >= ltAngle.getX() && pos.getX() <= ltAngle.getX() + dims.getX() &&
                pos.getY() >= ltAngle.getY() && pos.getY() <= ltAngle.getY() + dims.getY();
    }

}
