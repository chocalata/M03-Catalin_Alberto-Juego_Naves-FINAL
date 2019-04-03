package game.controller;

import StatVars.Resoluciones;
import game.Setter;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import game.model.Nave;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends Setter implements Initializable {

    @FXML Canvas canvas;
    GraphicsContext graphicsContext;

    //Si se ha pulsado alguna.
    private BooleanBinding anyPressed;

    //Teclas a pulsar
    private BooleanProperty leftPressed, rightPressed, upPressed, downPressed;

    Nave nave;

    @Override
    public void setScene(Scene scene){
        super.setScene(scene);
        scene.setOnMouseMoved(event->{
            nave.getOrientation().setPosY(event.getSceneY());
            nave.getOrientation().setPosX(event.getSceneX());
        });
        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.UP) {
                upPressed.set(true);
            }
            if (key.getCode() == KeyCode.DOWN) {
                downPressed.set(true);
            }
            if (key.getCode() == KeyCode.LEFT) {
                leftPressed.set(true);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                rightPressed.set(true);
            }
        });

        scene.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.UP) {
                upPressed.set(false);
            }
            if (key.getCode() == KeyCode.DOWN) {
                downPressed.set(false);
            }
            if (key.getCode() == KeyCode.LEFT) {
                leftPressed.set(false);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                rightPressed.set(false);
            }
        });

        start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        rightPressed = new SimpleBooleanProperty();
        leftPressed = new SimpleBooleanProperty();
        upPressed = new SimpleBooleanProperty();
        downPressed = new SimpleBooleanProperty();
        anyPressed = upPressed.or(downPressed).or(leftPressed).or(rightPressed);

        nave = new Nave(500,500, new Image("game/img/naves/navePlayer_1.png"), this.upPressed, this.downPressed, this.rightPressed, this.leftPressed);

        graphicsContext = canvas.getGraphicsContext2D();
    }
    Rotate rotate;

    private void start(){
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                if(anyPressed.get()) {
                    nave.mover();
                }

                graphicsContext.clearRect(0,0, Resoluciones.GAME_SCREEN_WIDTH, Resoluciones.GAME_SCREEN_HEIGHT);
                rotate = new Rotate(nave.getAngle(), nave.getPosX() + nave.getImgNave().getWidth()/2, nave.getPosY() + nave.getImgNave().getHeight()/2);

                //PROBLEMA: ¡¡ESTO GIRA TODA LA ESCENA!!
                graphicsContext.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());

                graphicsContext.drawImage(nave.getImgNave(), nave.getPosX(), nave.getPosY());

            }
        }.start();
    }
}
