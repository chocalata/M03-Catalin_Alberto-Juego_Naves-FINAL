package game.model;

import StatVars.Enums;
import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;

public class Arma {
    ///////UTILIZAR UN ARRAY DE BALAS Y QUE SE QUITEN CUANDO HAYAN SALIDO DE LA PANTALLA
    private ArrayList<Bala> balas;

    private ArrayList<Bala> balasToRemove;

    private GraphicsContext graphicsContext;

    public Arma(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
        balas = new ArrayList<>();
    }

    public void shoot(double x, double y, double cc, double co, double angle) {
        balas.add(new Bala(graphicsContext, x, y, cc, co, angle));
    }

    //borra las balas fuera de pantalla
    private void removeOOSBalas() {
        balasToRemove = new ArrayList<>();
        balas.forEach(bala -> {
            if(bala.getState() == Enums.BulletState.TO_REMOVE) {
                if(!balasToRemove.contains(bala)) {
                    balasToRemove.add(bala);
                }
            }
        });
        balasToRemove.forEach(bala -> balas.remove(bala));
    }

    public void update(){
        removeOOSBalas();
        if(!balas.isEmpty()) {
            balas.forEach(Bala::update);
        }
    }

    public void render(){
        if(!balas.isEmpty()) {
            balas.forEach(Bala::render);
        }
    }

    public ArrayList<Bala> getBalas() {
        return balas;
    }

    public ArrayList<Bala> getBalasToRemove() {
        return balasToRemove;
    }
}
