package ru.tutorial.other;

import java.util.Scanner;

public class SimpleThreadsAndIterruptions {
    //flag
    static boolean interrupted = false;

    public static void main(String[] args) {
        //t1 останавливается через t1.interrupt
        Thread t1 = new Thread(() -> {
            int tickNumber = 0;
            while (true) {
                try {
                    Thread.sleep(1000 / 3);
                } catch (InterruptedException ignored) {
                    break;
                }
                tickNumber++;
                System.out.println("Thread 1 tick " + tickNumber);
            }
        });
        t1.start();
        //останавливается через установку флага interrupted = true
        new Thread(() -> {
            int tickNumber = 0;

            while (!interrupted) {
                try {
                    Thread.sleep(1000 / 2);
                } catch (InterruptedException ignored) {
                }
                tickNumber++;
                System.out.println("Thread 2 tick " + tickNumber);
            }
        }).start();
        Scanner s = new Scanner(System.in);
        while (true){
            if(s.next().equals("e")){
                interrupted = true;
                t1.interrupt();
                break;
            }
        }

    }
}
