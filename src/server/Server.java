package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    DatagramSocket socket;
    int puerto;
    boolean acabat;

    public Server(int port){
        try {
            socket = new DatagramSocket(puerto);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        puerto = port;
        acabat = false;
    }

    public void runServer() throws IOException {

    }
}
