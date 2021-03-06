package ru.tutorial;
import java.util.*;
/*  Массивы(Arrays) используются для хранения списков значений определенного типа
    Массив хранит фиксированное количество элементов. Изначально все элемнты
    массива равны значению по умалчанию 0 - для чисел false для boolean null для ссылок
    Количество элементов в массиве называется его длинной или размером.
    Элементы массива занумерованы от 0 до длинны_массива - 1.
    Номера элементов массива так же называются индексами.
    Можно предстваить массив {12, 4, -2, 13, 3, 2, 6, 4, -1} в виде таблицы.
    индекс   | 0| 1| 2| 3| 4| 5| 6| 7| 8|
    значение |12| 4|-2|13| 3| 2| 6| 4|-1|                                           */
public class Lesson6Arrays {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        //Задание массива:
        //ТИП_ЭЛЕМЕНТОВ [] НАЗВАНИЕ = new ТИП_ЭЛЕМЕНТОВ [РАЗМЕР]; ИЛИ
        //ТИП_ЭЛЕМЕНТОВ [] НАЗВАНИЕ = {элемент_0, элемент_1, ...., элемент_N-1}
        int[] arr1 = new int[10];
        int[] arr2 = {3, 2, 1, 3, 4};
        //обращаться к элемнтам массива можно с помошью НАЗВАНИЕ_МАССИВА[номер_элемента]
        arr1[2] = 12;//Записать на место третьего(с индексом 2) элемента 12
        arr1[0]++; // увеличить первый элемнт на 1
        if (arr1[4] > 0) {
            System.out.println("Пятый элемент больше нуля");
        }
        System.out.println("Длинна массива: " + arr1.length);//так можно узнать длинну массива
        //Часто возникает необходимость сделать что-либо для каждого элемента массива
        //способ 1 - перечислить все индексы циклом for
        for (int i = 0; i < arr1.length; i++) {//тут делаем что-угодно с arr1[i]
            //например читаем новое значение элемента из консоли
            arr1[i] = keyboard.nextInt();
        }
        //способ 2 - использовать специальный вид цикла for
        //for(ТИП НАЗВАНИЕ : ИЗ_КАКОГО_МАССИВА){}
        for (int element : arr1) { //этот код будет сделан для каждого жлемента массива
            System.out.println(element); //печатаем элемент массива
        }
    }
}
