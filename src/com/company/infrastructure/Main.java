package com.company.infrastructure;

import com.company.infrastructure.client.Client;
import com.company.infrastructure.server.Server;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static final int SERVER_PORT = 7777;

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Willkommen bei HerzDame");
        System.out.println("Ex  fuer Spiel erstellen (x = Spieleranzahl)");
        System.out.println("B fuer Spiel beitreten");
        System.out.println("V fuer Verlassen");

        String input = in.nextLine();

        if (input.charAt(0) == 'E') {
            Server server = new Server(SERVER_PORT, Character.getNumericValue(input.charAt(1)));
            Thread serverThread = new Thread(server);
            serverThread.start();

            new Client("127.0.0.1", SERVER_PORT);
        }


        if (input.equals("B")) {
            System.out.println("Server IP angeben ");
            String serverIp = in.nextLine();

            new Client(serverIp, SERVER_PORT);
        }

        if (input.equals("V")) {
            System.out.println("Bye");
        }
    }
}