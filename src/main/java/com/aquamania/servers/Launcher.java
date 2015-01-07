package com.aquamania.servers;

import com.aquamania.servers.workers.WorkerEx;
import com.aquamania.servers.workers.WorkerIn;

/**
 * Created by lars__000 on 02-12-2014.
 */
public class Launcher {
    public static void  main(String[] args){
        MultiThreadedServer server = new MultiThreadedServer(9000);
        server.addWorkers(new WorkerIn(8080));
        server.addWorkers(new WorkerEx(9090));
        new Thread(server).start();

        try {
            Thread.sleep(5000 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }


}