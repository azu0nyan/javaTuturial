package ru.tutorial.exampleProjects.ccg;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    static Player p1;
    static Player p2;
    static Player current;
    static int turn = 1;
    static  Scanner s = new Scanner(System.in);

    static void nextTurn() {
        current = p1 == current ? p2 : p1;
        turn++;
    }

    static Player getOther(Player p) {
        return p == p1 ? p2 : p1;
    }

    public static void main(String[] args) {
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


        p1 = new Player("Player 1", deck1);
        p2 = new Player("Player 2", deck2);
        current = p1;

        while (p1.isAlive() && p2.isAlive()){
            System.out.println("Player " + current.name + " turn start turn number " + turn);
            current.turnStart();
            p1.printStatus();
            p2.printStatus();
            readAndProcessComannds();
            nextTurn();
            System.out.println("TURN END");
        }
    }

    public static void readAndProcessComannds(){
        System.out.println("Print `end` to end turn print `play handId tableId` to play card ");
        String command [] = s.nextLine().split(" {1,}");
        if(command.length == 0) readAndProcessComannds();
        else switch (command[0]){
            case "end":
                current.turnEnd();

                return;
            case "play":
                if(command.length >=3){
                    try {
                        int handId = Integer.parseInt(command[1]);
                        int tableId = Integer.parseInt(command[2]);
                        current.playCard(handId, tableId);
                    } catch (Exception ignored){
                        System.out.println("wrong format");
                    }
                } else {
                    System.out.println("wrong number of arguments");
                }
        }
        readAndProcessComannds();
    }
}
