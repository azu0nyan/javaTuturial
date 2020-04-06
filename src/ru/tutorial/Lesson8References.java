package ru.tutorial;

/**
 * Все типы делятся на 2 вида
 * -примитивные int, double, byte, short, float... пишутся с маленькой буквы, иде из подсвечивает их
 * -ссылочные - все остальные
 * <p>
 * В переменной ссылочного типа хранится не сам экземпляр класса, а место в памяти где находится какой-то экземпляр
 * -несколько переменных могут указывать на один и тот же экземпляр класса
 * -если несколько переменных указывают на один и тот же экземпляр то изменения в одной из них затрагивают все остальные
 * -переменная ссылочного типа может неуказвыать ни на один экземпляр, тогда её значение равно null
 * -обращение по ссылке равной null приводит к падению программы с  NullPointerException NPE
 *
 * Запустите пример ниже и разберитесь почему он делает именно такой вывод.
 */
class A {
    int b;
}

public class Lesson8References {
    public static void main(String[] args) {
        A a1 = new A();
        a1.b = 1;
        A a2 = new A();
        a2.b = 2;
        A a3 = new A();
        a3.b = 3;
        A an = null;
        //an.b = 4; приведет к падению программы
        System.out.println("--------------1----------");
        System.out.println(a1.b);
        System.out.println(a2.b);
        System.out.println(a3.b);

        a1 = a2;
        a2.b = 4;
        System.out.println("--------------2----------");
        System.out.println(a1.b);
        System.out.println(a2.b);
        System.out.println(a3.b);

        a1.b = 5;
        System.out.println("--------------3----------");
        System.out.println(a1.b);
        System.out.println(a2.b);
        System.out.println(a3.b);

        a1 = a3;
        a1.b = 6;
        System.out.println("--------------4----------");
        System.out.println(a1.b);
        System.out.println(a2.b);
        System.out.println(a3.b);

        a3.b = 7;
        a3 = new A();
        a3.b = 8;
        System.out.println("--------------5----------");
        System.out.println(a1.b);
        System.out.println(a2.b);
        System.out.println(a3.b);


        a1 = an;
        if(a1 == null){
            System.out.println("a1 == null");
        } else {
            System.out.println("a1 != null");
        }

        // ==
        a1 = new A();
        a1.b = 1;
        a2 = new A();
        a2.b = 1;
        a3 = a1;

        System.out.println(a1 == a2);
        System.out.println(a1 == a3);
        System.out.println(a2 == a3);

    }
}
