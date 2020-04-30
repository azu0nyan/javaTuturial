package ru.tutorial.exampleProjects.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);

        Socket server = new Socket("localhost", 1337);
        Scanner fromServer = new Scanner(server.getInputStream());
        PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);

        String c;
        while (!(c = fromServer.nextLine()).equals("mend")){
            System.out.println(c.substring(1));
            if(c.startsWith("a")){
                String from  = keyboard.next();
                String to  = keyboard.next();
                toServer.println(from);
                toServer.println(to);
            }
        }
    }
}
