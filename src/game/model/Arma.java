package game.model;

import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;

public class Arma {
    private Bala bala;

    ///////UTILIZAR UN ARRAY DE BALAS Y QUE SE QUITEN CUANDO HAYAN SALIDO DE LA PANTALLA
    ArrayList<Bala> balas;

    private GraphicsContext graphicsContext;

    public Arma(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;

    }

    public void shoot(double x, double y, double cc, double co, double angle) {
        bala = new Bala(graphicsContext, x, y, cc, co, angle);
    }

    public void update(){
        if(bala != null) {
            bala.update();
        }
    }



    public void render(){
        if(bala != null) {
            bala.render();
        }
    }
}
