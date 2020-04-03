package ru.tutorial;

import java.util.ArrayList;

interface  HasWeight{

    double getWeight();
}

interface HasName{
     String getName();
}
public class Lesson11AbstractClassesAndInterfaces {
    public static void main(String[] args) {
//        class Hotel extends Canteen, InOutPlace{}
//        class Hotel{
//            Canteen c;
//            InOutPlace ioPl;
//        }


        class File implements HasName{
            String name;
            public String getName() {
                return name;
            }
        }
        abstract class Food implements HasWeight{
        }

        class CannedTuna extends Food{
            public double getWeight() {
                return 0.2;
            }
        }
        class GlavProductTuna extends CannedTuna{

        }
        class GoodTuna extends CannedTuna{

        }

        class Vegetables extends Food{
            double weight;
            public double getWeight() {
                return weight;
            }
        }

        abstract class Animal implements HasWeight, HasName{
            double weight;
            String name = "Animal name";

            public boolean canEat(Food f){
                return true;
            }

            public String getName() {
                return name;
            }
            public double getWeight() {
                return weight;
            }

            abstract String saySmth();
        }
        abstract class Mammal extends Animal{
            double milkPerDay;
        }
        class Cat extends  Mammal{
            public boolean canEat(Food f) {
                if(f instanceof CannedTuna){
                    //typecast не упадет
                    CannedTuna ct = (CannedTuna) f;
                    return true;
                }
                return false;
            }

            String saySmth() {
                return "Meow";
            }
        }
        class Dog extends Mammal{
            String saySmth(){
                return "woof";
            }
        }
        class BigDog extends Dog{
            String saySmth(){
                return "WOOF";
            }

        }
        class Fox extends Mammal{
            String saySmth(){
                return "Frrrrr";
            }
        }

        ArrayList<Food> foods = new ArrayList<>();
        foods.add(new Vegetables());
        foods.add(new CannedTuna());
        foods.add(new Vegetables());
        foods.add(new GoodTuna());
        foods.add(new GlavProductTuna());
        ArrayList<Animal> animals = new ArrayList<>();
        Dog d = new Dog();
        animals.add(d);
        Fox f = new Fox();
        animals.add(f);
        animals.add(new BigDog());
        animals.add(new Cat());
//        new Animal();
        for (Animal animal : animals) {
            System.out.println(animal.saySmth());
        }
        ArrayList<HasWeight> all = new ArrayList<>();
        all.addAll(foods);
        all.addAll(animals);
        double w = 0;
        for (HasWeight hasWeight : all) {
            w += hasWeight.getWeight();
            if(hasWeight instanceof  HasName){
                System.out.println(((HasName)hasWeight).getName());
            }
        }
        System.out.println(w);

        for (Food food : foods) {
            if(d.canEat(food)){
                System.out.println("Dog eats " + food);
            } else {
                System.out.println("Dog wont eat " + food);

            }
        }
        Cat c = new Cat();
        for (Food food : foods) {
            if(c.canEat(food)){
                System.out.println("Cat eats " + food);
            } else {
                System.out.println("Car wont eat " + food);

            }
        }

    }
}
