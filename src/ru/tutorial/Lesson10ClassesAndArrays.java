package ru.tutorial;

import java.util.ArrayList;

class Car {
    String model;
    String number;

    public Car(String model, String number) {
        this.model = model;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
/*
 * Представьте что мы пишем программу для учета машин на парковке
 * На парковке есть фиксированное колличество мест
 * И есть заезд на парковку где машины выстраиваются в очередь, возможно пустую
 * В итоге мы имеем 2 разных по сути списка машин
 * Один фиксированной длинны, но элементы в нем могут быть "пустыми"
 * другой переменной длинны, но без "пустых" элементов
 *
 * для моделированния первого будем использовать обычный массив
 * для моделирования второго - ArrayList (Вообще эффективнее использовать LinkedList или Queue, но урок не об этом)
 */

public class Lesson10ClassesAndArrays {
    //Массив для хранения мест на стоянке
    //Car - ссылочный тип, значение по умолчанию для ссылочного типа null => в массиве хранится 10 null
    static Car[] parkingLots = new Car[10];
    //очередь для хранения машин заезжающих на стоянку
    static ArrayList<Car> queue = new ArrayList<>();

    //функция которая ищет свободное место на стоянке, возвращает -1 если не найдено
    static int findFreeLot() {
        for (int i = 0; i < parkingLots.length; i++) {
            if (parkingLots[i] == null) return i;
        }
        return -1;
    }

    //функция которая паркует машины из очереди
    static void parkCars() {
        while (queue.size() > 0) {
            int lot = findFreeLot();
            if (lot == -1) break;
            parkingLots[lot] = queue.remove(0);//удаляет первый элемент очереди и записывает его в parkingLots[Lot]
            System.out.println("Припаркована машина " + parkingLots[lot] + "  на место " + lot);
        }
    }

    static void unparkCar(Car c) {
        for (int i = 0; i < parkingLots.length; i++) {
            if (parkingLots[i] == c) {
                parkingLots[i] = null;
                System.out.println("Машина " + c + " уехала с парковки номер " + i);
            }
        }
    }

    public static void main(String[] args) {
        Car lada = new Car("lada", "2BBB33");
        queue.add(lada);
        queue.add(new Car("UAZ", "0ABC34"));
        queue.add(new Car("UAZ", "1ABC34"));
        queue.add(new Car("UAZ", "2ABC34"));
        queue.add(new Car("UAZ", "3ABC34"));
        queue.add(new Car("UAZ", "4ABC34"));
        queue.add(new Car("UAZ", "5ABC34"));
        queue.add(new Car("UAZ", "6ABC34"));
        queue.add(new Car("Mersedes", "7ABC34"));
        queue.add(new Car("Ferrari", "8ABC34"));
        queue.add(new Car("Kamaz", "9ABC34"));
        queue.add(new Car("Audi", "2AAA34"));
        parkCars();
        unparkCar(lada);
        parkCars();
    }
}
