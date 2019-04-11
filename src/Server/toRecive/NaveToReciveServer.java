package server.toRecive;

import game.model.toSend.BalaToSend;

import java.util.ArrayList;

public class NaveToReciveServer {
    // Identificador de la nave
    private int idNave;

    //posicion nave
    private double navePosX;
    private double navePosY;

    //posiciones cursor
    private double naveCursorPosX;
    private double naveCursorPosY;

    //angulo (nave y bala)
    private double angle;

    //balas
    private ArrayList<BalaToSend> naveArmaBalas;

    public int getIdNave() {
        return idNave;
    }

    public double getNavePosX() {
        return navePosX;
    }

    public double getNavePosY() {
        return navePosY;
    }

    public double getNaveCursorPosX() {
        return naveCursorPosX;
    }

    public double getNaveCursorPosY() {
        return naveCursorPosY;
    }

    public double getAngle() {
        return angle;
    }

    public ArrayList<BalaToSend> getNaveArmaBalas() {
        return naveArmaBalas;
    }

    @Override
    public boolean equals(Object obj) {
        return idNave == ((NaveToReciveServer) obj).getIdNave();
    }
}
