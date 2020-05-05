package ru.tutorial.exampleProjects.checkers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CheckersTextClient {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);

        Socket server = new Socket("localhost", 1337);
        Scanner fromServer = new Scanner(server.getInputStream());
        PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);

        String c;
        while (!(c = fromServer.nextLine()).equals("mend")){

            if(c.startsWith("a")){
                String from  = keyboard.next();
                String to  = keyboard.next();
                toServer.println(from);
                toServer.println(to);
                System.out.println(c.substring(1));
            } else if(c.startsWith("b")){
                String board  = c.substring(1);
                for(int i = 0; i < board.length(); i += CheckersGame.side * 2 +4){
                    System.out.println(board.substring(i, i + CheckersGame.side * 2 + 4));
                }


            } else {
                System.out.println(c.substring(1));
            }
        }
    }
}
