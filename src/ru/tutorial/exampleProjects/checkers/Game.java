package ru.tutorial.exampleProjects.checkers;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    static final int side = 8;
    static Pawn field[][] = new Pawn[side][side];

    static boolean currentPlayer;
    static boolean winner;

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        gameSetup();
        currentPlayer = true;
        while (gameOver() == null) {
            printField();
            System.out.println("Current player : " + (currentPlayer?"White":"Black"));
            readAndMove();
            currentPlayer = !currentPlayer;
        }
    }

    private static void readAndMove() {
        boolean success = false;
        while (!success) {
            ArrayList<Turn> turns = validTurns();
            System.out.println(turns);
            Turn t = Turn.readFromConsole();
            if (turns.contains(t)) {
                makeMove(t);
                success = true;
            }

        }
    }

    static void makeMove(Turn t) {
        field[t.toX][t.toY] = field[t.fromX][t.fromY];
        field[t.fromX][t.fromY] = null;
        if(currentPlayer && t.toX == side -1)field[t.toX][t.toY].isDame = true;
        if(!currentPlayer && t.toX == 0)field[t.toX][t.toY].isDame = true;
        if(t.len() == 2){
            int eatenX = (t.fromX + t.toX) / 2;
            int eatenY = (t.fromY + t.toY) / 2;
            field[eatenX][eatenY] = null;
            ArrayList<Turn> turns = eatingTurnsFrom(t.toX, t.toY);
            if(!turns.isEmpty()) {
                printField();
                System.out.println("You sould eat again " + turns);
                boolean success = false;
                while (!success){
                    Turn newT = Turn.readFromConsole();
                    if(turns.contains(newT)){
                        makeMove(newT);
                        success = true;
                    }
                }
            }
        }
    }

    static ArrayList<Turn> validTurns() {
        int dir = currentPlayer ? 1 : -1;
        ArrayList<Turn> move = new ArrayList<>();
        ArrayList<Turn> eat = new ArrayList<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] != null && field[i][j].color == currentPlayer) {
                    //двигаемся вперед(сейчас не работает для дамок)
                    if (i + dir >= 0 && i + dir < side) {
                        if (j - 1 >= 0 && field[i + dir][j - 1] == null) {
                            move.add(new Turn(i, j, i + dir, j - 1));
                        }
                        if (j + 1 < side && field[i + dir][j + 1] == null) {
                            move.add(new Turn(i, j, i + dir, j + 1));
                        }
                    }
                    //едим
                    eat.addAll(eatingTurnsFrom(i, j));
                }
            }
        }

        if (eat.isEmpty()) return move;
        else return eat;
    }

    //куда можно сходить сьев шашку с точки х у(сейчас не работает для дамок)
    static ArrayList<Turn> eatingTurnsFrom(int x, int y) {
        ArrayList<Turn> res = new ArrayList<>();
        if (x - 2 >= 0 && y - 2 >= 0 && field[x - 1][y - 1] != null && field[x - 2][y - 2] == null
                && field[x - 1][y - 1].color != field[x][y].color) {
            res.add(new Turn(x, y, x - 2, y - 2));
        }
        if (x - 2 >= 0 && y + 2 < side && field[x - 1][y + 1] != null && field[x - 2][y + 2] == null
                && field[x - 1][y + 1].color != field[x][y].color) {
            res.add(new Turn(x, y, x - 2, y + 2));
        }
        if (x + 2 < side && y - 2 >= 0 && field[x + 1][y - 1] != null && field[x + 2][y - 2] == null
                && field[x + 1][y - 1].color != field[x][y].color) {
            res.add(new Turn(x, y, x + 2, y - 2));
        }
        if (x + 2 < side && y + 2 < side && field[x + 1][y + 1] != null && field[x + 2][y + 2] == null
                && field[x + 1][y + 1].color != field[x][y].color) {
            res.add(new Turn(x, y, x + 2, y + 2));
        }
        return res;
    }

    //возвращает null если игра не закончена
    static Boolean gameOver() {
        boolean whiteHavePawn = false;
        boolean blackHavePawn = false;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] != null) {
                    if (field[i][j].color) whiteHavePawn = true;
                    else blackHavePawn = true;
                }
            }
        }
        if (whiteHavePawn && blackHavePawn) return null;
        return whiteHavePawn;
//        if(whiteHavePawn) return true;
//        else return false;
    }

    static void printField() {
        System.out.printf("   |");
        for (int i = 0; i < side; i++) {
            System.out.print((char) ('a' + i) + "|");
        }
        System.out.println();
        for (int i = 0; i < side; i++) {
            System.out.printf("%2d ", i + 1);
            System.out.printf("|");
            for (int j = 0; j < side; j++) {
                if (field[i][j] != null) {
                    if (!field[i][j].color) {
                        if (field[i][j].isDame) System.out.print("⚉");
                        else System.out.print("●");
                    } else {
                        if (field[i][j].isDame) System.out.print("⚇");
                        else System.out.printf("○");
                    }
                } else {
                    if ((i + j) % 2 == 0) {
                        System.out.print("■");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.printf("|");
            }
            System.out.println();
        }
    }

    static void gameSetup() {
        for (int i = 0; i <= 2; i++) {
            for (int j = i % 2; j < side; j += 2) {
                field[i][j] = new Pawn(true);
            }
        }

        for (int i = side - 3; i < side; i++) {
            for (int j = (i) % 2; j < side; j += 2) {
                field[i][j] = new Pawn(false);
            }
        }
    }
}
