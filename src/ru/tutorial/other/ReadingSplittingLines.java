package ru.tutorial.other;

import java.util.Scanner;

public class ReadingSplittingLines {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("WELCOME");
        System.out.println("Enter command");
        while (true){
            String cur = s.nextLine();
            String curArr [] = cur.split(" {1,}");
            if(curArr.length == 0){
                System.out.println("Empty string");
            } else if(curArr[0].equalsIgnoreCase("single")) {
                System.out.println(curArr[1]);
            } else if(curArr[0].equalsIgnoreCase("multiple")){
                for (int i = 1; i < curArr.length; i++) {
                    System.out.println(curArr[i]);
                }
            } else if(curArr[0].equalsIgnoreCase("multWithNum")){
                for (int i = 1; i < curArr.length-1; i++) {
                    System.out.println(curArr[i]);
                }
                int num = Integer.parseInt(curArr[curArr.length - 1]);
                System.out.println(num);
            } else if(curArr[0].equalsIgnoreCase("end")){
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }
}
