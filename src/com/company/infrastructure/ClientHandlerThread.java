package com.company.infrastructure;

import com.company.core.application.LegeKarte;
import com.company.core.application.ViewModels.ViewData;
import com.company.core.domain.Karte;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

public class ClientHandlerThread extends Thread
{
    private Socket socket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;

    private final int spielerPosition;
    private final LegeKarte legeKarte;

    private final Server server;

    public ClientHandlerThread(ServerSocket serverSocket, int spielerPosition, LegeKarte legeKarte, Server server) throws IOException {
        this.socket = serverSocket.accept();
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.objectOutputStream= new ObjectOutputStream(socket.getOutputStream());

        this.legeKarte = legeKarte;
        this.spielerPosition = spielerPosition;
        this.server = server; //TODO remove circular dependency;
    }

    public void run() {
        try {
            while (true) {
                String clientInput = dataInputStream.readUTF();
                Karte zuLegendeKarte = inputNachKarte(clientInput);
                legeKarte.invoke(spielerPosition, zuLegendeKarte);
                server.sendViewDataToAllClients();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendViewData(ViewData viewData) throws IOException {

        objectOutputStream.writeObject(viewData);
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

    public int getSpielerPosition() {
        return spielerPosition;
    }
}
