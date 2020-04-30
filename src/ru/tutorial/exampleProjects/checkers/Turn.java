package ru.tutorial.exampleProjects.checkers;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;
import static ru.tutorial.exampleProjects.checkers.Game.*;
public class Turn {

    int fromX;
    int fromY;
    int toX;
    int toY;

    int len(){
        return Math.abs(fromX - toX);
    }

    public Turn(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    static Turn readFromConsole(Scanner f, PrintWriter t) {
        t.println(askPrefix() +  "Enter `from to` ex `2e 3b`");
        String from = f.next();
        String to = f.next();
        return new Turn(from.charAt(0) - '0' -1, from.charAt(1) - 'a',
                to.charAt(0) - '0' -1, to.charAt(1) - 'a');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return fromX == turn.fromX &&
                fromY == turn.fromY &&
                toX == turn.toX &&
                toY == turn.toY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromX, fromY, toX, toY);
    }

    @Override
    public String
    toString() {
        return (fromX + 1) + "" + (char)(fromY + 'a') + " " + (toX + 1) + "" + (char)(toY + 'a');
    }
}
