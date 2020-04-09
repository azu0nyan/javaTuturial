package ru.tutorial.exampleProjects.ccg;

import java.util.ArrayList;
import java.util.Arrays;

public class MinionsCard extends Card{
    public MinionsCard(int hp, int attack, int mana) {
        super(hp, attack, mana);
    }

    Player parent;
    ArrayList<Card> minions = new ArrayList<>();
    @Override
    public void onPlacedOnTable() {
        parent = Game.current;
        for (int i = 0; i < parent.table.length; i++) {
            if(parent.table[i] != null){
                Card minion = new Card(1,1,0);
                minions.add(minion);
                parent.playCard(minion, i);
            }
        }
    }

    @Override
    public void onDeath() {
        for (Card minion : minions) {
            parent.die(minion);
        }
    }
}
