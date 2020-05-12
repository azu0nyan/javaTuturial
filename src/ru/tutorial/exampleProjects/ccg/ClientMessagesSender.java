package ru.tutorial.exampleProjects.ccg;
import java.io.PrintWriter;

//import static ru.tutorial.exampleProjects.ccg.Game.*;
//import static ru.tutorial.exampleProjects.ccg.сcgClient.CcgClient.enemyView;
//import static ru.tutorial.exampleProjects.ccg.сcgClient.CcgClient.fromServer;

public class ClientMessagesSender {

    static void sendWorldUpdate(){
        sendWorldUpdateToPlayer(Game.p1, Game.p2, Game.toP1);
        sendWorldUpdateToPlayer(Game.p2, Game.p1, Game.toP2);
    }

    static void sendWorldUpdateToPlayer(Player p1, Player p2, PrintWriter toP1){
        toP1.println("playerHand");
        toP1.println(p1.hand.size());
        for (Card card : p1.hand) {
            sendCard(toP1, card);
        }
        toP1.println("player");
        toP1.println(p1.getHp());
        toP1.println(p1.getMana());
        toP1.println(p1.deck.size());
        toP1.println("enemy");
        toP1.println(p2.getHp());
        toP1.println(p2.getMana());
        toP1.println(p2.deck.size());
        toP1.println(p2.hand.size());
        toP1.println("playerTable");
        sendTable(toP1, p1);
        toP1.println("enemyTable");
        sendTable(toP1, p2);
    }

    static void sendTable(PrintWriter to, Player p){
        for (int i = 0; i < p.table.length; i++) {
            if(p.table[i] == null){
                to.println("null");
            } else {
                to.println("card");
                sendCard(to, p.table[i]);
            }
        }
    }

    static void sendCard(PrintWriter to, Card c){
        String nameSplitted [] = c.getClass().getName().split("\\.");
        to.println(nameSplitted[nameSplitted.length - 1]);
        to.println(c.descr.replaceAll(" ", "~"));
        to.println(c.getHp());
        to.println(c.getMana());
        to.println(c.getAttack());
    }
}
