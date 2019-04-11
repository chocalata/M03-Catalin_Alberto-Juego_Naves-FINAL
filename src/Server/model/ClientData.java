package server.model;

import java.net.InetAddress;

public class ClientData {

    public ClientData(int idNave, int port) {
        this.idNave = idNave;
        this.port = port;
    }

    private int idNave;
    private int port;

    public int getIdNave() {
        return idNave;
    }

    public int getPort() {
        return port;
    }
}
