package com.company.infrastructure.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler implements Runnable{

    private final DataOutputStream dataOutputStream;

    public RequestHandler(Socket socket) throws IOException {
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);

        while(true) {
            try {
                String input = in.nextLine();
                dataOutputStream.writeUTF(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
