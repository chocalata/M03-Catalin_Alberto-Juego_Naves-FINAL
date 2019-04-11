package game.controller;

import StatVars.Resoluciones;
import game.GameSetter;
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

public class GameController extends GameSetter implements Initializable {

    @FXML Canvas canvas;

    //Datos que se mandan al servidor
    private NaveToSend dataToSend;

    private double time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        graphicsContext = canvas.getGraphicsContext2D();

        dataToSend = new NaveToSend();

        //Al ser 60 fotogramas por segundo, quiere decir que entre fotograma
        //y fotograma pasan 0.017 segundos más o menos.
        time = 0.01666666666;
    }

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
        canvas.setHeight(stage.getHeight());
        canvas.setWidth(stage.getWidth());
    }

    public void start(boolean isMultiplayer){
        if(isMultiplayer){
            startMultiplayer();
        }else {
            startSigle();
        }
    }

    private void startSigle(){
        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                nave.update();

                checkCollisions();

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());

                dataToSend.setData(nave, time);
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Transformer.classToJson(dataToSend));

                nave.render();

            }
        }.start();
    }

    private void startMultiplayer(){
        final long startNanoTime = System.nanoTime();

        //POR AQUI: AL COMENZAR EL JUEGO EN LINEA QUE HAGA ALL LO QUE TENGA QUE HACER
        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                nave.update();
                //recibeDatos;
                //navesRecibidas.update();
                checkCollisions();
                dataToSend.setData(nave, time);
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Transformer.classToJson(dataToSend).getBytes().length);
                //mandaDatos(dataToSend);

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());


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
