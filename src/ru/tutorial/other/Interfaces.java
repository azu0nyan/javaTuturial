package ru.tutorial.other;

public class Interfaces {
    public static void main(String[] args) {
       f2(new I2() {
           @Override
           public void a() {

           }

           @Override
           public void b() {

           }
       });
       f1(new I1() {
           @Override
           public void a() {

           }
       });

       f1(() -> {


       });


    }
    public static  void f2(I2 i){

    }
    public static  void f1(I1 i){

    }


}
interface I1{
    void a();
}
interface I2 {

    void a();
    void b();
}