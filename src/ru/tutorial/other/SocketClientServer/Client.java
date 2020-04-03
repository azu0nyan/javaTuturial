package ru.tutorial.other.SocketClientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static Scanner fromServer = null;
    static PrintWriter toServer = null;

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 1337);
        System.out.println("Connected to server");
        toServer = new PrintWriter(s.getOutputStream(), true);
        fromServer = new Scanner(s.getInputStream());

        new Thread(() -> {
            while (fromServer.hasNextLine()) {
                String command = fromServer.nextLine();
                processCommandFromServer(command);
            }
        }).start();

        new Thread(() -> {
            Scanner console = new Scanner(System.in);
            while (console.hasNextLine()){
                toServer.println(console.nextLine());
            }
        }).start();
    }


    public static void processCommandFromServer(String command) {
        System.out.println(command);
    }
}
