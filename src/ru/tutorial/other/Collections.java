package ru.tutorial.other;

import java.util.ArrayList;

public class Collections {
    public static void main(String[] args) {
        ArrayList<String> al = new ArrayList<>();
        al.add("a2");
        al.add("a3");
        al.add("a4");
        al.add("a5");
        int x = al.size();
        al.remove("a3");
        al.remove(2);
//        al.remove(Integer.valueOf(3));//by value
//        al.remove(3);//by index
        al.contains("a4");
        al.get(1);
        al.set(1,"a6");
    }
}
