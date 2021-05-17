package com.company.infrastructure.client;

import com.company.core.application.ViewModels.ViewData;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;

    public Client(String ipToConnect, int portToConnect) throws IOException {
        this.socket = new Socket(ipToConnect, portToConnect);

        this.requestHandler = new RequestHandler(socket);
        Thread inputHandlerThread = new Thread(requestHandler);
        inputHandlerThread.start();

        this.responseHandler = new ResponseHandler(socket);
        Thread responseHandlerThread = new Thread(responseHandler);
        responseHandlerThread.start();
    }
}