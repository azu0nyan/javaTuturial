package ru.tutorial.other;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

public class ArrayStreams {
    public static void main(String[] args) {
        int[] a = {1, 2, 2, 3, 4, 5, 5, -1};
//        a = a.map(_ * 2);
        //отображение
        a = Arrays.stream(a).map(x -> x * 2).toArray();
        System.out.println(Arrays.toString(a));
        //фильтрация
        a = Arrays.stream(a).filter(x -> x > 2).toArray();
        System.out.println(Arrays.toString(a));
        //свертка
        int sum = Arrays.stream(a).reduce((x, y) -> x + y).getAsInt();
        //
        System.out.println(sum);
        Arrays.stream(a).limit(10);//взять первые десять
        Arrays.stream(a).skip(10);//пропустить первые десять

        //сделать для каждого
        Arrays.stream(a).forEach(x -> System.out.println(x));

        int[] a2 = Stream.iterate(1, x -> x + 1).limit(20).mapToInt(x -> x).toArray();
        System.out.println(Arrays.toString(a2));

        int n = 3;
        int zz = Stream.iterate(1, z -> z + 1).limit(n).mapToInt(x -> {
                    if (x % 10 == 0) {
                        if (x % 16 == 0) return x * x;
                        else return x + 2;
                    } else {
                        if (x % 2 == 0) return 1;
                        else return -1;
                    }
                }).reduce(0, (q, w) -> q + w);
        System.out.println(zz);
    }
}












