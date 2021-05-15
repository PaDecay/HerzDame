package com.company.infrastructure;

import com.company.core.application.*;
import com.company.core.application.ViewModels.ViewData;
import com.company.core.domain.Karte;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;

    private StarteNeuesSpiel starteNeuesSpiel;
    private LegeKarte legeKarte;

    private GetViewData getViewData;
    private GetServerReadModel getServerReadModel;
    private int spieleranzahl;

    private SpielRepository inMemorySpielRepository;

    public Server(int port, int spieleranzahl) throws IOException {

        this.serverSocket = new ServerSocket(port);
        this.serverSocket.accept();

        for(int i = 0; i < spieleranzahl; i++) {
        }


        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        this.inMemorySpielRepository = new InMemorySpielRepository();

        //TODO use service container
        this.starteNeuesSpiel = new StarteNeuesSpiel(this.inMemorySpielRepository);
        this.legeKarte = new LegeKarte(this.inMemorySpielRepository);
        this.getViewData = new GetViewData(this.inMemorySpielRepository);
        this.spieleranzahl = spieleranzahl;
    }

    @Override
    public void run() {
        starteNeuesSpiel.invoke(spieleranzahl);

        try {
            runGameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runGameLoop() throws IOException {
        sendViewDataToAllClients();
        while (true) {
            String clientInput = this.dataInputStream.readUTF();
            Karte zuLegendeKarte = inputNachKarte(clientInput);
            legeKarte.invoke(zuLegendeKarte);
            sendViewDataToAllClients();

        }
    }

    private void sendViewDataToAllClients() throws IOException {
        ViewData clientViewData = getViewData.invoke(1);
        this.objectOutputStream.writeObject(clientViewData);
    }

    private Karte inputNachKarte(String karte) {
        String symbol = String.valueOf(karte.charAt(0));
        String rang = String.valueOf(karte.charAt(1));

        return new Karte(getKeyByValue(ViewData.SYMBOL_MAP, symbol), getKeyByValue(ViewData.RANG_MAP, rang));
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
