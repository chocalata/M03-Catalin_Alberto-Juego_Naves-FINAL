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

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends Setter implements Initializable {

    @FXML Canvas canvas;
    GraphicsContext graphicsContext;

    @Override
    public void setScene(Scene scene){
        super.setScene(scene);
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


    //Si se ha pulsado alguna.
    private BooleanBinding anyPressed;

    //Teclas a pulsar
    private BooleanProperty leftPressed;
    private BooleanProperty rightPressed;
    private BooleanProperty upPressed;
    private BooleanProperty downPressed;

    Image naveIMG;
    Nave nave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        naveIMG = new Image("game/img/naves/navePlayer_1.png");
        nave = new Nave(500,500);

        rightPressed = new SimpleBooleanProperty();
        leftPressed = new SimpleBooleanProperty();
        upPressed = new SimpleBooleanProperty();
        downPressed = new SimpleBooleanProperty();
        anyPressed = upPressed.or(downPressed).or(leftPressed).or(rightPressed);

        graphicsContext = canvas.getGraphicsContext2D();
    }

    private void start(){

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                if (upPressed.get()) {
                    nave.setPosY(nave.getPosY()-nave.SPEED);
                }
                if (downPressed.get()) {
                    nave.setPosY(nave.getPosY()+nave.SPEED);
                }
                if (rightPressed.get()) {
                    nave.setPosX(nave.getPosX()+nave.SPEED);
                }
                if (leftPressed.get()) {
                    nave.setPosX(nave.getPosX()-nave.SPEED);
                }

                graphicsContext.clearRect(0, 0, Resoluciones.GAME_SCREEN_WIDTH, Resoluciones.GAME_SCREEN_HEIGHT);
                graphicsContext.drawImage( naveIMG, nave.getPosX(), nave.getPosY());

            }
        }.start();
    }
}
