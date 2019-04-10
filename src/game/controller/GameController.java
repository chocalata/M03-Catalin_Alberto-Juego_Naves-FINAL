package game.controller;

import StatVars.Resoluciones;
import game.Setter;
import game.model.toSend.NaveToSend;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import game.model.Nave;
import javafx.stage.Stage;
import transformToJsonOrClass.Transformer;

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

    //Datos que se mandan al servidor
    NaveToSend dataToSend;

   private double time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rightPressed = new SimpleBooleanProperty();
        leftPressed = new SimpleBooleanProperty();
        upPressed = new SimpleBooleanProperty();
        downPressed = new SimpleBooleanProperty();
        anyPressed = upPressed.or(downPressed).or(leftPressed).or(rightPressed);

        graphicsContext = canvas.getGraphicsContext2D();

        dataToSend = new NaveToSend();

        time = 0.01666666666;
    }

    @Override
    public void setScene(Scene scene){
        super.setScene(scene);

        scene.setOnMouseReleased(event->{
            nave.shoot(event.getX(), event.getY());
        });

        scene.setOnMouseDragged(event->{
            nave.setOrientation(event.getX(), event.getY());
        });
        scene.setOnMouseMoved(event->{
            nave.setOrientation(event.getX(), event.getY());
        });
        scene.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.UP || key.getCode() == KeyCode.W) {
                upPressed.set(true);
            }
            if (key.getCode() == KeyCode.DOWN || key.getCode() == KeyCode.S) {
                downPressed.set(true);
            }
            if (key.getCode() == KeyCode.LEFT || key.getCode() == KeyCode.A) {
                leftPressed.set(true);
            }
            if (key.getCode() == KeyCode.RIGHT || key.getCode() == KeyCode.D) {
                rightPressed.set(true);
            }
        });

        scene.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.UP || key.getCode() == KeyCode.W) {
                upPressed.set(false);
            }
            if (key.getCode() == KeyCode.DOWN || key.getCode() == KeyCode.S) {
                downPressed.set(false);
            }
            if (key.getCode() == KeyCode.LEFT || key.getCode() == KeyCode.A) {
                leftPressed.set(false);
            }
            if (key.getCode() == KeyCode.RIGHT || key.getCode() == KeyCode.D) {
                rightPressed.set(false);
            }
        });

        start();
    }

    @Override
    public void setIdNave(int idNave) {
        super.setIdNave(idNave);

        nave = new Nave(graphicsContext,500,500, idNave,new ImageView("game/img/naves/navePlayer_1.png"), this.upPressed, this.downPressed, this.rightPressed, this.leftPressed, this.anyPressed);
        nave.setImagenRotada(nave.getImgNave().getImage());
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
                //Al ser 60 fotogramas por segundo, quiere decir que entre fotograma
                //y fotograma pasan 0.017 segundos más o menos.


                checkCollisions();

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());
                System.out.println(nave.getArma().getBalas().size());
                dataToSend.setData(nave, time);
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Transformer.classToJson(dataToSend));

                nave.render();

            }
        }.start();
    }

    private void checkCollisions(){
        checkNaveInScreen();
        checkBalaInScreen();

    }

    private void checkBalaInScreen() {
        nave.getArma().getBalas().forEach(bala -> {
            if(bala.getPosX() < 0){
                bala.remove();
            }else if(bala.getPosX() > stage.getWidth()){
                bala.remove();
            }
            if(bala.getPosY() < 0){
                bala.remove();
            }else if(bala.getPosY() > stage.getHeight()){
                bala.remove();
            }
        });
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
