package ru.tutorial.other;

import java.io.*;
import java.util.Scanner;

public class FileReadingWriting {
    public static void main(String[] args) throws IOException {
        new File("tmp").mkdir();
        readingWritingWithScannerAndPrintWriter();
        System.out.println("--------------------");
        readingWritingWithFileReaderWriter();
        System.out.println("\n--------------------");
        readingWritingWithFileIOStreams();
    }

    //Простой и удобный способ для записи текстовых данных
    static void readingWritingWithScannerAndPrintWriter() throws FileNotFoundException {

        //открываем файл для записи
        PrintWriter toFile = new PrintWriter(new File("tmp/FileReading.txt" ));
        toFile.println("Записываю данные в файл");
        toFile.println(5);
        toFile.println(true);
        toFile.println("строка 1");
        toFile.println("строка 2");
        toFile.println("последняя строка файла");
        toFile.close();//обязательно закрываем файл, иначе рискуем потерять то что записали

        Scanner fromFile = new Scanner(new File("tmp/FileReading.txt"));//открываем файл для чтения
        String headerLine = fromFile.nextLine();//читаем так же как и из консоли
        int five = fromFile.nextInt();
        boolean true_ = fromFile.nextBoolean();
        System.out.println("После строки числа и boolean в файле находится:");
        while (fromFile.hasNextLine()){
            System.out.println(fromFile.nextLine());
        }
        fromFile.close();//обязательно закрвыаем файл
    }


    //способ может пригодятся если нужно открыть файл для дозаписи
    static void readingWritingWithFileReaderWriter() throws IOException {
        //открываем файл для дозаписи
        FileWriter fw = new FileWriter("tmp/FileReading.txt",true );
        fw.write("Еще одна строка добавилась");
        fw.close();

        //Для чтения этот способ неудобен т.к. позволяет читать только посимсольно, или в предоставленный массив массив символов
        FileReader fr = new FileReader("tmp/FileReading.txt");
        int c;
        while ((c = fr.read()) >= 0) System.out.print((char)c);
        fr.close();
    }

    //способ позваляет записать в файл данные в виде набора байтов, так как они будут хранится на дискe
    static void readingWritingWithFileIOStreams() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("tmp/FileReading.txt"), false);
        for (int i = 0; i < 8; i++) {
            fos.write((1<<i) | (128 >>> i ));
        }
        fos.close();
        FileInputStream fis = new FileInputStream(new File("tmp/FileReading.txt"));
        int i = 0;
        while ((i = fis.read()) >= 0) {//читает -1 или число от 0 до 255, (но byte от -128 .. 127)
            byte b = (byte)(i & 0xFF);//конвертируем файл в байт
            String s = "";
            for (int j = 7; j >= 0; j--) s += (b >>> j) & 1;
            System.out.println(s);
        }
        fis.close();
    }
}
