package game.model;

import javafx.beans.property.BooleanProperty;

import java.awt.*;

import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Nave {
    private Cursor orientation;

    private double posX;
    private double posY;
    public final int SPEED = 3;
    private BooleanProperty upPressed, downPressed, rightPressed, leftPressed;
    private Image imgNave;

    public Nave(int posX, int posY, Image imgNave, BooleanProperty upPressed, BooleanProperty downPressed, BooleanProperty rightPressed, BooleanProperty leftPressed) {
        orientation = new Cursor();
        this.posX = posX;
        this.posY = posY;

        this.upPressed = upPressed;
        this.downPressed = downPressed;
        this.rightPressed = rightPressed;
        this.leftPressed = leftPressed;

        this.imgNave = imgNave;
    }

    public Cursor getOrientation() {
        return orientation;
    }

    public void setOrientation(int x, int y){
        orientation.setPosX(x);
        orientation.setPosY(y);
    }

    public Image getImgNave(){
        return imgNave;
    }
    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double pos){
        posX = pos;
    }
    public void setPosY(double pos){
        posY = pos;
    }

    public void mover(){
        if (upPressed.get()) {
            setPosY(getPosY() - SPEED);
        }
        if (downPressed.get()) {
            setPosY(getPosY() + SPEED);
        }
        if (leftPressed.get()) {
            setPosX(getPosX() - SPEED);
        }
        if (rightPressed.get()) {
            setPosX(getPosX() + SPEED);
        }

    }

    public double getAngle(){
//        double centerX = posX + imgNave.getHeight()/2;
//        double centerY = posY + imgNave.getWidth()/2;
//
//
//        double cc = (centerX - orientation.getPosX());
//        double co = (centerY - orientation.getPosY());
//
//        return Math.toDegrees(Math.atan2(co, cc))-90;
        return Math.toDegrees(Math.atan2(((posY + imgNave.getWidth()/2) - orientation.getPosY()), ((posX + imgNave.getHeight()/2) - orientation.getPosX())))-90;

    }
}
