package ru.tutorial.other;

import java.util.Timer;
import java.util.TimerTask;

public class PassTime {
    public static void main(String[] args) {
        //нужно сделать действие спустя какое-то время, например 3 секунды

        //способ 1 потоки
        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("DOING TASK HERE 1");
        }).start();
        //способ 2 таймер
        Timer t = new Timer(true);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("DOING TASK HERE 2");
            }
        }, 5000);

        //способ 3 без отдельных потоков, хороший для игр с main gameLoop
        final long start = System.currentTimeMillis();
        boolean taskComplete = false;
        while (true){
            //делаем другие вещи
            //делаем другие вещи
            //делаем другие вещи
            //делаем другие вещи
            final long now = System.currentTimeMillis();
            if(!taskComplete && now - start >= 5000){
                System.out.println("DOING TASK HERE 3");
                taskComplete = true;
            }

        }



    }
}
