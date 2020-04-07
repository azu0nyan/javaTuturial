package ru.tutorial.exampleProjects.ccg;

public class BlockingCard extends Card{
    public BlockingCard(int hp, int attack, int mana) {
        super(hp, attack, mana);
    }

    @Override
    public void modifyHp(int dHP) {
        if(dHP >= 0){
            super.modifyHp(dHP);
        } else {
            super.modifyHp(-1);
        }
    }
}
