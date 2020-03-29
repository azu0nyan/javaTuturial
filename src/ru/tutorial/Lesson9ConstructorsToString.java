package ru.tutorial;

//Давайте создадим класс с информацией о машине
class LongCarInfo {
    String model;
    double maxSpeed;
    double currentSpeed;
    int wheels;
    String fuelType;
    String driverName;

    /* получилось довольно много полей, задавать их через . для каждой машины будет неужобно
     * для более удобного задания начальных значений полей создадим конструктор
     * конструктор это функция которая назвается ТАК ЖЕ как класс, и НЕ ИМЕЕТ возвращаемого типа
     * тогда при использовании new Car() будет использован конструктор, а в его скобки нужно будет подставить его параметры*/
    public LongCarInfo(String model, double maxSpeed, String driverName) {
        //этот код выполнится при создании нового экземпляра класса
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.driverName = driverName;
        this.currentSpeed = 0;
        this.wheels = 4;
        this.fuelType = "gas";
        //  this. model используется чтобы отличить поле класса model от параметра функции model
    }
    /*
     * -конструкторов может быть несколько
     * -если в классе определены конструкторы то вы обязаны их использовать
     * -IDEA умеет сама генерировать конструктор Alt+Insert -> Constructor
     */

    /*
     *  Java не знает что печатать когда вы заставляете её печатать экземпляры ваших классов,
     * можно её помочь определив функцию toString.
     * В IDEA Alt+Insert -> toString
     * Рекомедуется отредактировть полученный результат под ваши нужды
     */
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", currentSpeed=" + currentSpeed +
                ", wheels=" + wheels +
                ", fuelType='" + fuelType + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}

public class Lesson9ConstructorsToString {

    public static void main(String[] args) {
        //Примеры использования
        LongCarInfo c1 = new LongCarInfo("Audi", 200, "Andrey");
        LongCarInfo c2 = new LongCarInfo("Mersedes", 220, "Yaroslav");
        LongCarInfo c3 = new LongCarInfo("Ferrary", 320, "Vova");
        LongCarInfo c4 = new LongCarInfo("UAZ", 150, "Maxim");


        //во всех случаях будет использована описанная нами функция toString для конвертации в строку
        System.out.println(c1);
        System.out.println(c2.toString());
        System.out.println(String.valueOf(c3));
        System.out.println("Всякий текст " + c4);
    }
}
