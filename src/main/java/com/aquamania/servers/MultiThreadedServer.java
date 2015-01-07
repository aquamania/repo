package com.aquamania.servers;


import com.aquamania.servers.workers.Worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class MultiThreadedServer implements Runnable {

    protected int serverPort = 8080;
    protected ArrayList<ServerSocket> serverSockets = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    protected ArrayList<Worker> workers = null;

    protected AtomicInteger atomicInteger = new AtomicInteger(1);

    public MultiThreadedServer(int port) {
        this.serverPort = port;
    }

    public ArrayList<Worker> addWorkers(Worker worker) {

        if (this.workers == null) {
            this.workers = new ArrayList<Worker>();
        }

        this.workers.add(worker);

        return workers;
    }

    public void removeWorkers(Worker worker) {

        if (this.workers != null) {
            this.workers.remove(worker);
        }
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSockets();
        while (!isStopped()) {
            Socket clientSocket = null;
            RuntimeException runEx = null;
            for (ServerSocket serverSock : this.serverSockets) {
                try {

                    clientSocket = serverSock.accept();
                } catch (IOException e) {
                    if (isStopped()) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    runEx = new RuntimeException(
                            "Error accepting client connection", runEx);
                }
             if(runEx != null)
                 throw runEx;
                for (Worker worker : workers) {
                    if(worker.getPort() == serverSock.getLocalPort()){
                        Worker work = null;
                        try {
                            work = worker.getClass().newInstance();
                            work.setPort(worker.getPort());
                            work.setClientSocket(clientSocket);
                            work.setServerText("(" + atomicInteger.getAndIncrement() +") Multithreaded Server port " + worker.getPort());

                            new Thread(work).start();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

        }
        }
        System.out.println("Servers Stopped.");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;

            for (ServerSocket serverSock : this.serverSockets)
            try{
                serverSock.close();
            } catch(IOException e){
                throw new RuntimeException("Error closing server", e);
            }

    }

    private void openServerSockets() {

        this.serverSockets = new ArrayList<ServerSocket>();
        this.serverSockets.ensureCapacity(256*256-1);
        int port = 0;
        for (Worker worker : workers) {
            try {
                port = ((Worker) worker).getPort();
                this.serverSockets.add( new ServerSocket(port));


            } catch (IOException e) {
                throw new RuntimeException("Cannot open port " + port, e);
            }
        }
    }

}