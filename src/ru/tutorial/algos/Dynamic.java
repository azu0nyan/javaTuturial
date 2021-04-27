package ru.tutorial.algos;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Dynamic {
    //Динамическое программирование

    static int [] arr = new int[100];
    static {
        Arrays.fill(arr, -1);

    }

    static int  fib(int n){
        if(n <= 1) return 1;
        if(arr[n] < 0){
            arr[n] = fib(n - 1) + fib(n - 2);
        }

        return  arr[n];
    }

    static void fibArr(){
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            arr[i] = arr[i -1] + arr[i - 2];
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 15; i++) {
            System.out.println(fib(i));
        }
    }

}
