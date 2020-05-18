package ru.tutorial.exampleProjects.ccg;

public class ForbidDrawCardCard extends Card {
    public ForbidDrawCardCard(int hp, int attack, int mana) {
        super(hp, attack, mana);
    }


    Player enemy;
    @Override
    public void onPlacedOnTable() {
        enemy = CcgGameServer.getOther(CcgGameServer.current);
        enemy.forbidCardDrawSources.add(this);
    }


    @Override
    public void onDeath() {
        enemy.forbidCardDrawSources.remove(this);
    }
}
