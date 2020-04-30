package ru.tutorial.exampleProjects.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    static final int side = 8;
    static Pawn field[][] = new Pawn[side][side];

    static boolean currentPlayer;
    static boolean winner;
    static boolean multilayer = false;

    static String askPrefix() {
        return multilayer ? "a" : "";
    }

    static String msgPrefix() {
        return multilayer ? "m" : "";
    }

    static Scanner fromConsole = new Scanner(System.in);
    static Scanner fromP1 = fromConsole;
    static Scanner fromP2 = fromConsole;

    static Scanner fromCurrent() {
        return currentPlayer ? fromP1 : fromP2;
    }


    static PrintWriter toConsole = new PrintWriter(System.out, true);
    static PrintWriter toP1 = new PrintWriter(new DummyWriter(), true);
    static PrintWriter toP2 = new PrintWriter(new DummyWriter(), true);

    static PrintWriter toCurrent() {
        return currentPlayer ? toP1 : toP2;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Enter game mode (H)ot seat, (M)ultiplayer");
        String gameMode = fromConsole.nextLine();
        if (gameMode.equalsIgnoreCase("H")) {
            System.out.println("Hot seat mode selected");
        } else {
            System.out.println("Multiplayer mode selected");
            multilayer = true;
            ServerSocket serverSocket = new ServerSocket(1337);
            System.out.println("Server started waiting to connection...");

            Socket p1Socket = serverSocket.accept();
            System.out.println("player 1 connected");
            fromP1 = new Scanner(p1Socket.getInputStream());
            toP1 = new PrintWriter(p1Socket.getOutputStream(), true);
            toP1.println(msgPrefix() + "welcome player 1 your color is WHITE");//greet

            Socket p2Socket = serverSocket.accept();
            System.out.println("player 2 connected");
            fromP2 = new Scanner(p2Socket.getInputStream());
            toP2 = new PrintWriter(p2Socket.getOutputStream(), true);
            toP2.println(msgPrefix() + "welcome player 2 enter your color is BLACK:");//greet
        }
        startGame();
    }


    private static void startGame() {
        gameSetup();
        currentPlayer = true;
        while (gameOver() == null) {
            printField();
            toConsole.println("Current player : " + (currentPlayer ? "White" : "Black"));
            toP1.println(msgPrefix() + "Current player : " + (currentPlayer ? "White" : "Black"));
            toP2.println(msgPrefix() + "Current player : " + (currentPlayer ? "White" : "Black"));
            readAndMove();
            currentPlayer = !currentPlayer;
        }
        toConsole.println("end");
        toP1.println(msgPrefix() + "end");
        toP2.println(msgPrefix() + "end");
    }

    private static void readAndMove() {
        boolean success = false;
        while (!success) {
            ArrayList<Turn> turns = validTurns();
            toConsole.println(turns);
            toCurrent().println(msgPrefix() + turns);
            Turn t = Turn.readFromConsole(fromCurrent(), toCurrent());
            if (turns.contains(t)) {
                makeMove(t);
                success = true;
            }

        }
    }

    static void makeMove(Turn t) {
        field[t.toX][t.toY] = field[t.fromX][t.fromY];
        field[t.fromX][t.fromY] = null;
        if (currentPlayer && t.toX == side - 1) field[t.toX][t.toY].isDame = true;
        if (!currentPlayer && t.toX == 0) field[t.toX][t.toY].isDame = true;
        if (t.len() == 2) {
            int eatenX = (t.fromX + t.toX) / 2;
            int eatenY = (t.fromY + t.toY) / 2;
            field[eatenX][eatenY] = null;
            ArrayList<Turn> turns = eatingTurnsFrom(t.toX, t.toY);
            if (!turns.isEmpty()) {
                printField();
                toConsole.println("You sould eat again " + turns);
                toCurrent().println(msgPrefix() + "You sould eat again " + turns);
                boolean success = false;
                while (!success) {
                    Turn newT = Turn.readFromConsole(fromCurrent(), toCurrent());
                    if (turns.contains(newT)) {
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
        String res = "";
        res += "   |";
        for (int i = 0; i < side; i++) {
            res += ((char) ('a' + i) + "|");
        }
        res += "\n";
        for (int i = 0; i < side; i++) {
            res += String.format("%2d ", i + 1);
            res += ("|");
            for (int j = 0; j < side; j++) {
                if (field[i][j] != null) {
                    if (!field[i][j].color) {
                        res += field[i][j].isDame ? "⚉" : "●";
                    } else {
                        res += field[i][j].isDame ? "⚇" : "○";
                    }
                } else {
                    res += ((i + j) % 2 == 0) ? "■" : " ";
                }

                res += "|";
            }
            res += "\n";
        }
        if(multilayer){
            String [] toSend = res.split("\n");
            for (String s : toSend) {
                toP1.println(msgPrefix() + s);
                toP2.println(msgPrefix() + s);
            }
        }
        toConsole.println(res);
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
