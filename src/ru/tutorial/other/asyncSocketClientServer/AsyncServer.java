package ru.tutorial.other.asyncSocketClientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class AsyncServer {

    static int x1Speed = 0;
    static int x1 = 100;
    static int y1 = 300;

    static int x2Speed = 0;
    static int x2 = 100;
    static int y2 = 100;


    public static void tick() {
        x1 += x1Speed;
        x2 += x2Speed;

    }


    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1337);
        System.out.println("Server started waiting to connection...");

        Socket p1Socket = serverSocket.accept();
        System.out.println("player 1 connected");
        Scanner fromP1 = new Scanner(p1Socket.getInputStream());
        PrintWriter toP1 = new PrintWriter(p1Socket.getOutputStream(), true);

        Socket p2Socket = serverSocket.accept();
        System.out.println("player 2 connected");
        Scanner fromP2 = new Scanner(p2Socket.getInputStream());
        PrintWriter toP2 = new PrintWriter(p2Socket.getOutputStream(), true);


        new Thread(() -> {
            while (true) {
                switch (fromP1.next()) {
                    case "LEFT":
                        x1Speed = -1;
                        break;
                    case "RIGHT":
                        x1Speed = 1;
                        break;
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                switch (fromP2.next()) {
                    case "LEFT":
                        x2Speed = -1;
                        break;
                    case "RIGHT":
                        x2Speed = 1;
                        break;
                }
            }
        }).start();


        while (true) {
            tick();
            //world x1 x2 y1 y2
            //info some info
            //chat hello
            toP1.println(x1);
            toP1.println(y1);
            toP1.println(x2);
            toP1.println(y2);

            toP2.println(x1);
            toP2.println(y1);
            toP2.println(x2);
            toP2.println(y2);
            Thread.sleep(1000 / 60);
        }
    }
}
