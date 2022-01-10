package ru.tutorial.other;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalInterface {

    public static int supplier(){
        return new Random().nextInt(10);
    }

    public static void consumer(int x){
        System.out.println(x);
    }

    public static String f (int x){
        return String.valueOf(x);
    }

    public static void main(String[] args) {
        int[] a = {2, 2, 3, 4, 5, 6};
        Supplier<Integer> s = FunctionalInterface::supplier;
        Consumer<Integer> c = FunctionalInterface::consumer;
        Function<Integer, String> func = FunctionalInterface::f;

        s.get();
        supplier();

        c.accept(23);
        consumer(23);

        func.apply(234);
        f(234);


        Consumer<Integer> c2 = x -> System.out.println(x);
        doWithN(23, c);
        doWithN(23, c2);
        doWithN(23, x -> System.out.println(x));
        doWithN(23, System.out::println);
        doWithN(23, x ->{
            int y = x + 2;
            y = 2 * y;
            if(y > 5){
                System.out.println(y);
            }
        });
//        doWithN(23, c);

    }

    //Функция высшего порядка
    public static void doWithN(int n, Consumer<Integer> cons){
        for (int i = 1; i <= n; i++) {
            cons.accept(i);
        }
    }
}
