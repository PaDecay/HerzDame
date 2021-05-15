package com.company.infrastructure;

import com.company.core.application.ViewModels.ViewData;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;

    private DataOutputStream dataOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(String ipToConnect, int portToConnect) throws IOException {
        this.socket = new Socket(ipToConnect, portToConnect);

        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            ViewData viewData = null;
            try {
                viewData = (ViewData) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            assert viewData != null;
            renderView(viewData);
        }
    }

    private void renderView(ViewData viewData) {
        System.out.println("######################################################");
        System.out.println("Ablagekarte: ");
        System.out.println("[" + viewData.ablagekarte + "]");

        System.out.println("Handkarten: ");
        for (int i = 0; i < viewData.handkarten.length; i++) {
            System.out.print("[" + viewData.handkarten[i] + "]");
        }
        System.out.println();
        System.out.println("Spieler am Zug: ");
        System.out.println(viewData.spielerAmZug);
    }


}
