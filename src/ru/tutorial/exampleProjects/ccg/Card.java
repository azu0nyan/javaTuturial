package ru.tutorial.exampleProjects.ccg;

public class Card implements HasHp {

    public Card(int hp, int attack, int mana) {
        this.attack = attack;
        this.mana = mana;
        this.hp = hp;
        descr = "Some card with " + attack + " attack" + " and " + mana +" mana.";
    }

    String descr;

    private int attack;
    private int mana;
    private int hp;


    @Override
    public String toString() {
        return "Card{" +
                "attack=" + getAttack() +
                ", mana=" + getMana() +
                ", hp=" + getHp() +
                '}';
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void modifyAttack(int dAttack) {
        attack += dAttack;
    }

    public int getMana() {
        return mana;
    }

    public int getHp() {
        return hp;
    }

    public void modifyHp(int dHP) {
        hp += dHP;
    }

    @Override
    public boolean isAlive() {
        return getHp() > 0;
    }

    public void onPlacedOnTable() {

    }

    public void onDeath(){

    }

    public void onTurnStart(){

    }

    public void onTurnEnd(){

    }
}
