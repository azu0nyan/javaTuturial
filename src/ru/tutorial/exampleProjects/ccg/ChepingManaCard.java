package ru.tutorial.exampleProjects.ccg;

public class ChepingManaCard extends Card {
    public ChepingManaCard(int hp, int attack, int mana) {
        super(hp, attack, mana);
    }

    @Override
    public int getMana() {
        return Math.max(super.getMana() - Game.turn, 0);
    }
}
