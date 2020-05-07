package ru.tutorial.exampleProjects.ccg;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static ru.tutorial.exampleProjects.ccg.Game.*;
public class Player implements HasHp {


    public static final int tableSize = 6;
    PrintWriter toMe;
    Scanner fromMe;


    ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> hand = new ArrayList<>();
    Card[] table = new Card[tableSize];


    String name;
    private int mana = 0;
    private int hp = 30;

    ArrayList<Object> forbidCardDrawSources = new ArrayList<>();


    public Player(String name, ArrayList<Card> deck, Scanner fromMe, PrintWriter toMe) {
        this.deck = deck;
        this.name = name;
        this.fromMe = fromMe;
        this.toMe = toMe;
    }

    void  die(Card c){
        for (int i = 0; i < table.length; i++) {
            c.onDeath();
            if(table[i] == c ) table[i] = null;
        }
    }

    void printStatus() {
        String msg =  "Player: " + name + " mana: " + getMana() + " hp" + getHp();
        toMe.println(msgPrefix +msg);
        System.out.println(msg);
        String msg2 =  "hand" + hand;
        System.out.println(msg2);
        toMe.println(msgPrefix +msg2);
        System.out.println(Arrays.toString(table));
    }

    public int getHp() {
        return hp;
    }

    public void modifyHp(int dHP) {
        hp += dHP;
        String msg ="Player " + name + " hp changed by " + dHP + " current hp " + hp;
        toMe.println( msgPrefix +  msg);
        System.out.println(msg);
        getOther(this).toMe.println(msg);
    }

    public void modifyMana(int dMana) {
        mana += dMana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    void drawCard() {
        if (deck.size() > 0 && forbidCardDrawSources.isEmpty()) {
            hand.add(deck.remove(0));
        }
    }

    boolean playCard(int handId, int tableId) {
        if (hand.size() > handId && hand.get(handId).getMana() >= getMana() && table[tableId] == null) {
            modifyMana(-hand.get(handId).getMana());
            String msg = ("Player " + name + " playing card " + hand.get(handId));
            toMe.println(msgPrefix + msg);
            System.out.println(msg);
            getOther(this).toMe.println(msg);
            Card card = hand.remove(handId);
            playCard(card, tableId);
            System.out.println(Arrays.toString(table));
            return true;
        }
        return false;
    }

    void playCard(Card card, int tableId){
        table[tableId] = card;
        card.onPlacedOnTable();
    }

    void turnStart() {
        setMana(Game.turn + 1);
        drawCard();
        for (Card card : table) {
            if(card !=null) card.onTurnStart();
        }
    }

    void turnEnd() {
        Player other = Game.getOther(this);
        for (Card card : table) {
            if(card !=null) card.onTurnEnd();
        }
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                int dmg = table[i].getAttack();
                if (other.table[i] != null) {
                    other.table[i].modifyHp(-dmg);
                } else {
                    other.modifyHp(-dmg);
                }
            }
        }

    }

    public boolean isAlive() {
        return getHp() > 0;

    }

}
