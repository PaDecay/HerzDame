package com.company.infrastructure.server;

import com.company.core.application.*;
import com.company.core.application.UseCases.LegeKarte;
import com.company.core.application.UseCases.StarteNeuesSpiel;
import com.company.core.application.ViewModels.ViewData;
import com.company.infrastructure.repository.InMemorySpielRepository;

import java.io.*;
import java.net.ServerSocket;

public class Server implements Runnable{

    private ServerSocket serverSocket;

    private StarteNeuesSpiel starteNeuesSpiel;
    private LegeKarte legeKarte;

    private final GetViewData getViewData;

    private SpielRepository inMemorySpielRepository;
    private ClientHandlerThread[] clientHandlerThreads;
    private int spieleranzahl;

    public Server(int port, int spieleranzahl) throws IOException {
        //Setup sockets
        this.serverSocket = new ServerSocket(port);

        // Setup application services TODO use service container
        this.inMemorySpielRepository = new InMemorySpielRepository();
        this.starteNeuesSpiel = new StarteNeuesSpiel(this.inMemorySpielRepository);
        this.legeKarte = new LegeKarte(this.inMemorySpielRepository);
        this.getViewData = new GetViewData(this.inMemorySpielRepository);
        starteNeuesSpiel.invoke(spieleranzahl);
        //Setup client-threads
        this.clientHandlerThreads = new ClientHandlerThread[spieleranzahl];

        this.spieleranzahl = spieleranzahl;
    }

    @Override
    public void run() {
        try {
            for(int spielerPos = 0; spielerPos < spieleranzahl; spielerPos++) {
                ClientHandlerThread c = null; // TODO remove circular dependency
                c = new ClientHandlerThread(serverSocket, spielerPos, this.legeKarte, this);
                c.start();
                clientHandlerThreads[spielerPos] = c;
            }

            sendViewDataToAllClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendViewDataToAllClients() throws IOException {
        for (ClientHandlerThread c : clientHandlerThreads) {
            ViewData clientViewData = getViewData.invoke(c.getSpielerPosition());
            c.sendViewData(clientViewData);
        }
    }
}
