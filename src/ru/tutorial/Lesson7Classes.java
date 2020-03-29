package ru.tutorial;

import java.util.Random;

/**
 * !!В данном пример все классы описаны в этом файле, в реальности лучше описывать каждый класс в своем файле
 * Класс состоит из:
 * -Данных(переменных и констант)
 * -Методов(функций) для раобы с этими данными
 * Сейчас мы используем класс чтобы хранить много разной информации об объекте в одной переменной.
 * <p>
 * Определим простой класс "ученик" с полями "имя" и "последняяОценка"
 * и методами "вывести информацию об ученике", "получить последнюю оценку в кубе"
 */
class Student {
    String name;//поле имя(name) класса Student, типа строка(String)
    int lastGrade;//поле последняя оценка (lastGrade), типа целое число(int)

    //метод "printShortInfo" выводящий информацию об ученике
    void printShortInfo() {
        System.out.println("Ученик " + name + " " + " последняя оценка " + lastGrade);
    }

    int getLastGradeCubed() {
        return lastGrade * lastGrade * lastGrade;
    }
}

/*
 * Сейчас мы описали шаблон для любого ученика которого будет обрабатывать наша программа.
 * По сути мы создали новый Тип - Student, мы можем его использовать наравне с другими(int, double, char [], String, Scanner, ..)
 * Если вдруг вашей программе нужно обрабатывать 2,3,4...н учеников, то новый класс создавать не нужно.
 * Нужно для каждого из них создать свой экземпляр класса(см. main ниже).
 *
 * !!!!Обратите внимание что в классе нет ключевого слова "static",
 * оно нужно чтобы обозначать глобальные переменные и функции,
 * т.к. у каждого ученика свое имя и своя последняя оценка "static" тут не нужен.
 */
public class Lesson7Classes {
    public static void main(String[] args) {
        //создать "объект типа Student" или "экземпляр класса Student" можно так.
        new Student();
        //Это создаст в памяти компьютера данные которые мы описали name и lastGrade
        //но у нас пока нет переменной через которую мы можем к ним обратиться, создадим её по такому шаблону:
        //Тип название = объектТипаТип;
        //а "new Тип();" создаст нам объект нужного типа, то пишем вот так в общем виде:
        //Тип название = new Тип();
        //И для класса Student
        Student student1 = new Student();
        //теперь мы можем обратится к полям нашего класса
        student1.name = "Alexey";
        student1.lastGrade = 4;
        //и воспользоваться его методами
        System.out.println("Вывожу информацию о первом студенте");
        student1.printShortInfo();
        System.out.println("Его последняя оценка в кубе " + student1.getLastGradeCubed());
        //Если мы хотим создать еще одного студента в нашей программе, то ни в кокм случае не создаем новый класс, а
        //создаем новый экземпляр класса, например так:
        Student student2 = new Student();

        student2.name = "Vasya";
        student2.lastGrade = 3;

        System.out.println("Вывожу информацию о втором студенте");
        student2.printShortInfo();
        System.out.println("Его последняя оценка в кубе " + student2.getLastGradeCubed());

        //Приведем пример использоания функций описанных ниже
        Student student3 = genRandom();
        student3.printShortInfo();
        Student student4 = genRandom();
        student4.printShortInfo();

        Student best12 = getBest(student1, student1);
        Student best34 = getBest(student3, student4);
        Student absoluteBest = getBest(best12, best34);
        System.out.println("Самый лучший из 4х");
        absoluteBest.printShortInfo();

    }

    //Классы можно использовать как аргументы функции, и как её результат
    //Давайте напишем функцию которая создает нам случайного студента
    //обращаю внимание что эта функция static, т.к. она глобальная, для её работы ненужны никакие данные никакого экземпляра класса
    static Student genRandom() {
        Random r = new Random();
        String names[] = {"Andrey", "Alexandr", "Igor", "Petr", "Kirill"};
        String name = names[r.nextInt(names.length)];
        int lastGrade = r.nextInt(4) + 2;
        Student result = new Student();
        result.name = name;
        result.lastGrade = lastGrade;
        return result;
    }

    //другая функция берет двух студентов и возвращает того у которого больше последняя оценка
    static Student getBest(Student st1, Student st2) {
        if (st1.lastGrade > st2.lastGrade) return st1;
        else return st2;
    }


}













