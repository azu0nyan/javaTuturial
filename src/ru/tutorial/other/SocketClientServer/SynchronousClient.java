package ru.tutorial.other.SocketClientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SynchronousClient {
    public static void main(String[] args) throws IOException {
        //Client            Internet       Server
        //String -> Byte -> Send -> Byte -> String
        //Int    ->                      -> Int
        Scanner keyboard = new Scanner(System.in);

        Socket server = new Socket("localhost", 1337);
        Scanner fromServer = new Scanner(server.getInputStream());
        PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);

        System.out.println(fromServer.nextLine());//greet
        String name = keyboard.nextLine();
        toServer.println(name);//send name to server

        System.out.println(fromServer.nextLine());//ask food
        String food = keyboard.nextLine();
        toServer.println(food);

        System.out.println(fromServer.nextLine());//show other player food


    }
}
