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
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import game.model.Nave;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

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

    //Nave
    Nave nave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rightPressed = new SimpleBooleanProperty();
        leftPressed = new SimpleBooleanProperty();
        upPressed = new SimpleBooleanProperty();
        downPressed = new SimpleBooleanProperty();
        anyPressed = upPressed.or(downPressed).or(leftPressed).or(rightPressed);

        nave = new Nave(500,500,new ImageView("game/img/naves/navePlayer_1.png"), this.upPressed, this.downPressed, this.rightPressed, this.leftPressed);

        nave.setImagenRotada(nave.getImgNave().getImage());

        graphicsContext = canvas.getGraphicsContext2D();

    }

    @Override
    public void setScene(Scene scene){
        super.setScene(scene);

        scene.setOnMouseDragged(event->{
            nave.getOrientation().setPosY(event.getY());
            nave.getOrientation().setPosX(event.getX());
        });
        scene.setOnMouseMoved(event->{
            nave.getOrientation().setPosY(event.getY());
            nave.getOrientation().setPosX(event.getX());
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
    public void setStage(Stage stage) {
        super.setStage(stage);
        canvas.setHeight(stage.getHeight());
        canvas.setWidth(stage.getWidth());
    }

    private void start(){
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                if(anyPressed.get()) {
                    nave.mover();
                }
                nave.rotate();
                checkCollisions();

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());

                graphicsContext.drawImage(nave.getImagenRotada(), nave.getPosX(), nave.getPosY());

                graphicsContext.drawImage(nave.getImagenRotada(),
                        nave.getPosX() + nave.getImgNave().getImage().getWidth(),
                        nave.getPosY() + nave.getImgNave().getImage().getHeight());


            }
        }.start();
    }

    private void checkCollisions(){
        checkNaveInScreen();
    }

    private void checkNaveInScreen() {
        if(nave.getPosX() < 0){
            nave.setPosX(0);
        }else if(nave.getPosX() + nave.getImgNave().getImage().getWidth() + Resoluciones.AJUSTAR_PANTALLA_X > stage.getWidth()){
            nave.setPosX(stage.getWidth() - nave.getImgNave().getImage().getWidth() - Resoluciones.AJUSTAR_PANTALLA_X);
        }
        if(nave.getPosY() < 0){
            nave.setPosY(0);
        }else if(nave.getPosY() + nave.getImgNave().getImage().getHeight() + Resoluciones.AJUSTAR_PANTALLA_Y > stage.getHeight()){
            nave.setPosY(stage.getHeight() - nave.getImgNave().getImage().getHeight() - Resoluciones.AJUSTAR_PANTALLA_Y);
        }
    }
}
