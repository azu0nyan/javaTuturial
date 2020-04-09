package ru.tutorial.exampleProjects.ccg;

public class HpRegenCard extends Card{
    public HpRegenCard(int hp, int attack, int mana) {
        super(hp, attack, mana);
    }

    @Override
    public void onTurnStart() {
        modifyHp(1);
    }
}
