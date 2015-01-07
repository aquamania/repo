package com.aquamania.servers.workers;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**

 */
public abstract class AbstractWorker implements Worker {


    protected Socket clientSocket = null;
    protected String serverText = null;



    protected int port = 8080;

    public AbstractWorker() {
        this.clientSocket = null;
        this.serverText = null;
    }
    public AbstractWorker(int port) {
        this.clientSocket = null;
        this.serverText = null;
        this.port = port;
    }
    public AbstractWorker(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            process(input,output);
            output.close();
            input.close();

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setServerText(String serverText) {
        this.serverText = serverText;
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}