package ru.tutorial.exampleProjects.ccg;

public class DecreaseEnemyAttackCard extends Card {
    public DecreaseEnemyAttackCard(int hp, int attack, int mana) {
        super(hp, attack, mana);
    }

    @Override
    public void onPlacedOnTable() {
        Card[] other = Game.getOther(Game.current).table;
        for (Card card : other) {
            if (card != null) card.modifyAttack(-1);
        }
    }

}
