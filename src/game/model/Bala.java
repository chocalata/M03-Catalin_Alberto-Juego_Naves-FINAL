package game.model;

import StatVars.Resoluciones;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.Array;

public class Bala {

    private double posX;
    private double posY;

    private double coseno;
    private double seno;

    private final double speed = 30;

    private GraphicsContext graphicsContext;

    private Image imagenRotada;

    private ImageView imgBala;

    private double angle;

    public Bala(GraphicsContext graphicsContext, double x, double y, double cc, double co, double angle){

        this.angle = angle;

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);

        imgBala = new ImageView("game/img/bala.png");
        System.out.println(angle);
        imgBala.setRotate(angle);
        imagenRotada = imgBala.snapshot(snapshotParameters, null);

        this.graphicsContext = graphicsContext;

        posX = x;
        posY = y;

        coseno = (cc + Resoluciones.AJUSTAR_CATETOS)/Math.hypot(cc, co);
        seno = (co + Resoluciones.AJUSTAR_CATETOS)/Math.hypot(cc, co);

    }

    public void move(){
        posX -= coseno * speed;
        posY -= seno * speed;

        //System.out.println(posX + " " + posY);
    }

    public void update(){
        move();
    }

    public void render(){
        graphicsContext.drawImage(imagenRotada, posX, posY);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
