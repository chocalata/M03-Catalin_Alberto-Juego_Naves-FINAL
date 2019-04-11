package server;

import game.model.Meteorito;
import game.model.Nave;

import java.lang.reflect.Array;

public class EstadoJuego {
    Nave nave1;
    Nave nave2;

    public EstadoJuego (Nave nave1, Nave nave2) {
        this.nave1 = nave1;
        this.nave2 = nave2;
    }
}