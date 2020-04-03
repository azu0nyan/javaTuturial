package ru.tutorial.other.SocketClientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class FancyServer {
    static CopyOnWriteArrayList<FancyClient> clients = new CopyOnWriteArrayList<>();
    static int maxID = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(1337);
        while (true) {
            final  int userId = maxID;
            maxID++;
            final Socket socket = serverSocket.accept();
            System.out.println("New connection " + socket);
            final Scanner fromClient = new Scanner(socket.getInputStream());
            final PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
            new Thread(() -> {
                String name = fromClient.nextLine();
                FancyClient client = new FancyClient(name, socket, toClient, fromClient);
                clients.add(client);
                while (fromClient.hasNextLine()) {
                    String msg = "MESSAGE FROM " + userId + " " + name + " : " +fromClient.nextLine();
                    System.out.println(msg);
                    for (FancyClient otherClient : clients) {
                        if(otherClient != client){
                            otherClient.toClient.println(msg);
                        }
                    }
                }
            }).start();

            //fromClient = null;
        }
    }
}
