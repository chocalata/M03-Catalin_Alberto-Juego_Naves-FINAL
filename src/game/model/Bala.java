package game.model;

import StatVars.Enums;
import StatVars.Resoluciones;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bala {
    private Enums.BulletState state;

    private double posX;
    private double posY;

    private double cos;
    private double sin;

    private final double speed = 15;

    private GraphicsContext graphicsContext;

    private Image imagenRotada;
    
    private boolean added;

    public Bala(GraphicsContext graphicsContext, double x, double y, double cc, double co, double angle){
        //Para saber si la he añadido dentro del JSON.
        added = false;
        
        state = Enums.BulletState.SHOOTING;

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);

        ImageView imgBala = new ImageView("game/img/bala.png");
        System.out.println(angle);
        imgBala.setRotate(angle);
        imagenRotada = imgBala.snapshot(snapshotParameters, null);

        this.graphicsContext = graphicsContext;

        posX = x;
        posY = y;

        cos = (cc + Resoluciones.AJUSTAR_CATETOS)/Math.hypot(cc, co);
        sin = (co + Resoluciones.AJUSTAR_CATETOS)/Math.hypot(cc, co);

    }

    private void move(){
        posX -= cos * speed;
        posY -= sin * speed;

        //System.out.println(posX + " " + posY);
    }

    public void remove(){
        state = Enums.BulletState.TO_REMOVE;
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

    public double getCos() {
        return cos;
    }

    public double getSin() {
        return sin;
    }

    public Enums.BulletState getState() {
        return state;
    }

    public boolean getAdded() {
        return added;
    }

    public void setAddedTrue() {
        added = true;
    }
}
