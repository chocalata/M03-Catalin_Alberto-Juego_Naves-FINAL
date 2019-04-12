package formatClasses;

import game.model.toSend.BalaToSend;

import java.util.ArrayList;

public class NaveToRecive {
    // Identificador de la nave
    protected int idNave;

    //posicion nave
    protected double navePosX;
    protected double navePosY;

    //posiciones cursor
    protected double naveCursorPosX;
    protected double naveCursorPosY;

    //angulo (nave y bala)
    protected double angle;

    //balas
    protected ArrayList<BalaToSend> naveArmaBalas;

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
        return idNave == ((NaveToRecive) obj).getIdNave();
    }

    @Override
    public String toString() {
        return "ID: " + idNave + "\nAngulo: " + angle;
    }
}
