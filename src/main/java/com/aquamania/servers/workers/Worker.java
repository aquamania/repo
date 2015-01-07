package com.aquamania.servers.workers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by lars__000 on 12-12-2014.
 */
public interface Worker extends Runnable {
    public void setClientSocket(Socket clientSocket);
    public void setServerText(String serverText);
    public void process(InputStream is,OutputStream os) throws IOException;
    public int getPort();
    public void setPort(int port);
}
