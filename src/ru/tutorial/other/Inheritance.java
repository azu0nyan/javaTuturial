package ru.tutorial.other;

public class Inheritance {
    public static void main(String[] args) {
        abstract class  Animal{

            boolean isFat(){
                return weight > 10;
            }

            double weight;

            abstract void say();
        }

        class Dog extends Animal{

            @Override
            void say() {
                System.out.println("WOOF");
            }


        }
        class Cat extends Animal{
            @Override
            void say() {
                System.out.println("MEOW");
            }
        }

        Animal [] animals = {new Dog(), new Dog(), new Cat()};
    }
}
