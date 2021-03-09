package ru.tutorial.other;

import java.util.HashMap;

public class Maps {
    public static void main(String[] args) {
        HashMap<String, String> engToRus = new HashMap<>();
        engToRus.put("cat", "кошакен");
        engToRus.put("dog", "собакен");

        if(engToRus.containsKey("cat")){
            System.out.println(engToRus.get("cat"));
        }

        for(String eng : engToRus.keySet()){
            System.out.println(eng + " " + engToRus.get(eng));
        }

    }
}
