package com.aquamania.servers.workers;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**

 */
public class WorkerEx extends AbstractWorker {

    public WorkerEx() {
        super();
    }
    public WorkerEx(int port) {
        super(port);
    }
    public WorkerEx(Socket clientSocket, String serverText) {
        super(clientSocket,serverText);
    }



    public void process(InputStream is,OutputStream os) throws IOException {

        Date date = new Date();
        os.write(("HTTP/1.1 200 OK\n\nWorkerExRunnable: " +
                this.serverText + " - " +
                date +
                "").getBytes());

        System.out.println("Request processed: " + date);
    }
}