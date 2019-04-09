package server.toRecive;

import game.model.toSend.BalaToSend;

import java.util.ArrayList;

public class NaveToRecive {
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
}
