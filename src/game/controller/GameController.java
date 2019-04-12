package game.controller;

import StatVars.Resoluciones;
import game.GameSetter;
import game.model.toSend.NaveToSend;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import formatClasses.NaveToRecive;
import Transformmer.Transformer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
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
            try {
                startMultiplayer();
            } catch (SocketException e) {
                e.printStackTrace();
            }
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

    private void startMultiplayer() throws SocketException {
        DatagramSocket socket = new DatagramSocket();

        //POR AQUI: AL COMENZAR EL JUEGO EN LINEA QUE HAGA ALL LO QUE TENGA QUE HACER
        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {

                nave.update();

                dataToSend.setData(nave, time);

                String sendData = Transformer.classToJson(dataToSend);
                packet = new DatagramPacket(sendData.getBytes(),
                        sendData.getBytes().length,
                        ipServer,
                        portServer);
                try {
                    socket.send(packet);

                    packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateNavesRecibidas(packet);

                checkCollisions();

                dataToSend.setData(nave, time);

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());

                nave.render();

            }
        }.start();
    }

    private void updateNavesRecibidas(DatagramPacket packet){
        try {
            ArrayList<NaveToRecive> navesRecived = Transformer.jsonToArrayListNaves(Transformer.packetDataToString(packet));
            navesRecived.forEach(nave->{
                if(this.nave.getId() != nave.getIdNave()){
                    graphicsContext.drawImage(new Image("game/img/naves/navePlayer_" + nave.getIdNave() + ".png"), nave.getNavePosX(), nave.getNaveCursorPosY());
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
