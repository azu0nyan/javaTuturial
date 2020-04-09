package ru.tutorial.exampleProjects.ccg;

import java.util.ArrayList;
import java.util.Arrays;

public class Player implements HasHp {

    ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> hand = new ArrayList<>();
    Card[] table = new Card[6];


    String name;
    private int mana = 0;
    private int hp = 30;

    ArrayList<Object> forbidCardDrawSources = new ArrayList<>();


    public Player(String name, ArrayList<Card> deck) {
        this.deck = deck;
        this.name = name;
    }

    void  die(Card c){
        for (int i = 0; i < table.length; i++) {
            c.onDeath();
            if(table[i] == c ) table[i] = null;
        }
    }

    void printStatus() {
        System.out.println("Player: " + name + " mana: " + getMana() + " hp" + getHp());
        System.out.println("hand" + hand);
        System.out.println(Arrays.toString(table));
    }

    public int getHp() {
        return hp;
    }

    public void modifyHp(int dHP) {
        hp += dHP;
        System.out.println("Player " + name + " hp changed by " + dHP + " current hp " + hp);
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
            System.out.println("Player " + name + " playing card " + hand.get(handId));
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
