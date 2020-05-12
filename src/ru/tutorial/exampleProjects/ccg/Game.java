package ru.tutorial.exampleProjects.ccg;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {


    static Player p1;
    static Player p2;
    static Player current;
    static int turn = 1;
    static Scanner s = new Scanner(System.in);


    static void nextTurn() {
        for (int i = 0; i < p1.table.length; i++) {
            if (p1.table[i] != null && !p1.table[i].isAlive()) {
                p1.die(p1.table[i]);
            }
        }
        for (int i = 0; i < p2.table.length; i++) {
            if (p2.table[i] != null && !p2.table[i].isAlive()) {
                p2.die(p2.table[i]);
            }
        }
        current = p1 == current ? p2 : p1;
        turn++;
    }

    static Player getOther(Player p) {
        return p == p1 ? p2 : p1;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        System.out.println("Server started waiting to connection...");

        Socket p1Socket = serverSocket.accept();
        System.out.println("player 1 connected");
        fromP1 = new Scanner(p1Socket.getInputStream());
        toP1 = new PrintWriter(p1Socket.getOutputStream(), true);

        Socket p2Socket = serverSocket.accept();
        System.out.println("player 2 connected");
        fromP2 = new Scanner(p2Socket.getInputStream());
        toP2 = new PrintWriter(p2Socket.getOutputStream(), true);
        startGame();
    }
    static Scanner fromP1;
    static PrintWriter toP1;
    static Scanner fromP2;
    static PrintWriter toP2;



    public static void startGame() {
        ArrayList<Card> deck1 = new ArrayList<>();
        deck1.add(new BlockingCard(10, 4, 2));
        deck1.add(new ChepingManaCard(10, 4, 8));
        deck1.add(new Card(10, 4, 2));
        deck1.add(new Card(10, 4, 2));

        ArrayList<Card> deck2 = new ArrayList<>();
        deck2.add(new Card(10, 4, 2));
        deck2.add(new Card(10, 4, 2));
        deck2.add(new Card(10, 4, 2));
        deck2.add(new Card(10, 4, 2));


        p1 = new Player("Player 1", deck1, fromP1, toP1);
        p2 = new Player("Player 2", deck2, fromP2, toP2);
        current = p1;

        while (p1.isAlive() && p2.isAlive()) {
            String msg = "Player " + current.name + " turn start turn number " + turn;
            System.out.println(msg);
//            p1.toMe.println(msgPrefix + msg);
//            p2.toMe.println(msgPrefix + msg);
            ClientMessagesSender.sendWorldUpdate();

            current.turnStart();

            p1.printStatus();
            p2.printStatus();

            readAndProcessComannds();

            nextTurn();
            System.out.println("TURN END");
//            p1.toMe.println(msgPrefix + "TURN END");
//            p2.toMe.println(msgPrefix + "TURN END");
        }

        System.out.println("END");
        p1.toMe.println("end");
        p2.toMe.println("end");
    }


    public static void readAndProcessComannds() {
        System.out.println("Waiting player input....");
//        current.toMe.println(actPrefix + "Print `end` to end turn print `play handId tableId` to play card ");
        current.toMe.println("act");
        String command[] = current.fromMe.nextLine().split(" {1,}");
        System.out.println("Player input: " + Arrays.toString(command));
        if (command.length == 0) readAndProcessComannds();
        else switch (command[0]) {
            case "end":
                current.turnEnd();

                return;
            case "play":
                if (command.length >= 3) {
                    try {
                        int handId = Integer.parseInt(command[1]);
                        int tableId = Integer.parseInt(command[2]);
                        current.playCard(handId, tableId);
                    } catch (Exception ignored) {
                        System.out.println( "wrong format");
                    }
                } else {
                    System.out.println( "wrong number of arguments");
                }
        }
        readAndProcessComannds();
    }
}
