package ru.tutorial.other.SocketClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    static CopyOnWriteArrayList<PrintWriter> clients = new CopyOnWriteArrayList<>();
    static int maxID = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(1337);
        while (true) {
            final  int userId = maxID;
            maxID++;
            Socket socket = serverSocket.accept();
            System.out.println("New connection " + socket);
            Scanner fromClient = new Scanner(socket.getInputStream());
            clients.add(new PrintWriter(socket.getOutputStream(), true));
            new Thread(() -> {
                while (fromClient.hasNextLine()) {
                    String msg = "MESSAGE FROM " + userId + " : " +fromClient.nextLine();
                    System.out.println(msg);
                    for (PrintWriter client : clients) {
                        client.println( msg);
                    }
                }
            }).start();

        }
    }
}
