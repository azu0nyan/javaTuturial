package ru.tutorial.other.SocketClientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SynchronousServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        System.out.println("Server started waiting to connection...");

        Socket p1Socket = serverSocket.accept();
        System.out.println("p1 connected");
        Scanner fromP1 = new Scanner(p1Socket.getInputStream());
        PrintWriter toP1 = new PrintWriter(p1Socket.getOutputStream(), true);
        toP1.println("welcome player 1 enter your name:");//greet

        Socket p2Socket = serverSocket.accept();
        System.out.println("player 2 connected");
        Scanner fromP2 = new Scanner(p2Socket.getInputStream());
        PrintWriter toP2 = new PrintWriter(p2Socket.getOutputStream(), true);
        toP2.println("welcome player 2 enter your name:");//greet


        String p1Name = fromP1.nextLine(); //ask name
        String p2Name = fromP2.nextLine(); //ask name

        System.out.println("Player 1 name: " + p1Name);
        System.out.println("Player 2 name: " + p2Name);


        toP1.println("Enter your favourite food");//ask food
        toP2.println("Enter your favourite food");//ask food

        String p1Food = fromP1.nextLine();
        String p2Food = fromP2.nextLine();

        toP1.println("p2's favourite " + p2Food);//show others food
        toP2.println("p1's favourite " + p1Food);//show others food

    }
}
