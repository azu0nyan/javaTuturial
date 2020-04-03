package ru.tutorial.other.SocketClientServer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class FancyClient {
    String name;
    Socket socket;
    PrintWriter toClient;
    Scanner fromClient;

    public FancyClient(String name, Socket socket, PrintWriter toClient, Scanner fromClient) {
        this.name = name;
        this.socket = socket;
        this.toClient = toClient;
        this.fromClient = fromClient;
    }

}
