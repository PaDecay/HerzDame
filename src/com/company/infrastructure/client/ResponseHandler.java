package com.company.infrastructure.client;

import com.company.core.application.ViewModels.ViewData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ResponseHandler implements Runnable {

    private final ObjectInputStream objectInputStream;

    public ResponseHandler(Socket socket) throws IOException {
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            ViewData viewData = null;
            try {
                System.out.println("8");
                viewData = (ViewData) objectInputStream.readObject();
                System.out.println("9");
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
