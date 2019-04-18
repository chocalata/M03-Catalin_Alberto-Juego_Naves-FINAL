package game.controller;

import StatVars.Packets;
import StatVars.Resoluciones;

import game.GameSetter;
import game.model.toSend.NaveToSend;
import game.services.MeteorService;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import formatClasses.NaveToRecive;
import Transformmer.Transformer;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameController extends GameSetter implements Initializable {

    @FXML Canvas canvas;

    //Datos que se mandan al servidor
    private NaveToSend dataToSend;
    private double time;

    private Map<Integer, Image> imagenRotadaOtrasNaves;
    private Map<Integer, ImageView> imagenOtrasNaves;
    private SnapshotParameters snapshotParameters;
    private SnapshotParameters snapshotParametersBalas;

    private ImageView imagenBala;

    private ArrayList<NaveToRecive> navesRecived;

    private byte[] recivingData;
    private MeteorService meteorService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        graphicsContext = canvas.getGraphicsContext2D();

        dataToSend = new NaveToSend();

        //Al ser 60 fotogramas por segundo, quiere decir que entre fotograma
        //y fotograma pasan 0.017 segundos más o menos.
        time = 0.01666666666;

        recivingData = new byte[Packets.PACKET_LENGHT];

        imagenOtrasNaves = new HashMap<>();
        imagenRotadaOtrasNaves = new HashMap<>();
        snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);

        snapshotParametersBalas = new SnapshotParameters();
        snapshotParametersBalas.setFill(Color.TRANSPARENT);

        imagenBala = new ImageView("game/img/bala.png");
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

    private double timingMeteoritos = 0;
    private double anteriorCurrentNanoTime = 0;

    private void startSigle(){
        final long startNanoTime = System.nanoTime();
        meteorService = new MeteorService(scene.getWidth(),scene.getHeight(),graphicsContext);

        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                if(anteriorCurrentNanoTime == 0){
                    anteriorCurrentNanoTime = currentNanoTime;
                }
                timingMeteoritos += (currentNanoTime-anteriorCurrentNanoTime)*Math.pow(10, -9);
                anteriorCurrentNanoTime = currentNanoTime;

                if(timingMeteoritos > 1) {
                    meteorService.create(nave.getPosX()+(nave.getImagenRotada().getWidth())/2, nave.getPosY()+(nave.getImagenRotada().getHeight())/2);
                    timingMeteoritos = 0;
                }

                nave.update(false);
                meteorService.update();

                checkCollisions();

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());

                nave.render();
                meteorService.render();

            }
        }.start();
    }

    private void startMultiplayer() throws SocketException {
        DatagramSocket socket = new DatagramSocket();

        //POR AQUI: AL COMENZAR EL JUEGO EN LINEA QUE HAGA ALL LO QUE TENGA QUE HACER
        new AnimationTimer() {
            public void handle(long currentNanoTime)
            {

                graphicsContext.clearRect(0,0, stage.getWidth(), stage.getHeight());

                nave.update(checkCollisionNaves());

                dataToSend.setData(nave, time);
                dataToSend.getNaveArmaBalas().forEach(balaToSend -> System.out.println(balaToSend.getAngle()));

                String sendData = Transformer.classToJson(dataToSend);
                packet = new DatagramPacket(sendData.getBytes(),
                        sendData.getBytes().length,
                        ipServer,
                        portServer);
                try {
                    socket.send(packet);
                    socket.setSoTimeout(500);
                    packet = new DatagramPacket(recivingData, Packets.PACKET_LENGHT);
                    socket.receive(packet);

                    navesRecived = Transformer.jsonToArrayListNaves(Transformer.packetDataToString(packet));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                renderNavesRecibidas();

                checkCollisions();

                //dataToSend.setData(nave, time);

                nave.render();

            }
        }.start();
    }

    private void renderNavesRecibidas(){
        navesRecived.forEach(nave->{
            if(this.nave.getId() != nave.getIdNave()) {
                if (!imagenOtrasNaves.containsKey(nave.getIdNave())) {
                    imagenOtrasNaves.put(nave.getIdNave(), new ImageView("game/img/naves/navePlayer_" + nave.getIdNave() + ".png"));
                    imagenRotadaOtrasNaves.put(nave.getIdNave(), new Image("game/img/naves/navePlayer_" + nave.getIdNave() + ".png"));
//                        rotateNaveRecibida(nave.getIdNave(), nave.getAngle());
//                        graphicsContext.drawImage(imagenRotadaOtrasNaves.get(nave.getIdNave()), nave.getNavePosX(), nave.getNavePosY());
//
//                        nave.getNaveArmaBalas().forEach(bala -> {
//                            graphicsContext.drawImage(rotateBalaRecibida(bala.getAngle()), bala.getPosX(), bala.getPosY());
//
//                            System.out.println(bala.getPosX() + "  " + bala.getPosY());
//                            System.out.println(bala.getAngle());
//                        });

                    drawRecivedData(nave);
                }else {
                    drawRecivedData(nave);
                }
            }
        });

    }


    private void drawRecivedData(NaveToRecive nave) {
        rotateNaveRecibida(nave.getIdNave(), nave.getAngle());
        graphicsContext.drawImage(imagenRotadaOtrasNaves.get(nave.getIdNave()), nave.getNavePosX(), nave.getNavePosY());
        nave.getNaveArmaBalas().forEach(bala -> {
            graphicsContext.drawImage(rotateBalaRecibida(bala.getAngle()), bala.getPosX(), bala.getPosY());
            System.out.println(bala.getPosX() + "  " + bala.getPosY());
            System.out.println(bala.getAngle());
        });
    }

    private void rotateNaveRecibida(int id, double angle){
        imagenOtrasNaves.get(id).setRotate(angle);
        imagenRotadaOtrasNaves.put(id, imagenOtrasNaves.get(id).snapshot(snapshotParameters, null));
    }

    private Image rotateBalaRecibida(double angle){
        imagenBala.setRotate(angle);
        return imagenBala.snapshot(snapshotParameters, null);
    }

    private void checkCollisions(){
        checkNaveInScreen();
        checkCollisionBala();
        checkCollisionNaves();
        checkCollisionMeteor();

    }

    private void checkCollisionMeteor() {
        //Se puede juntar el contenido de este método y el de checkCollisionBala
        meteorService.getMeteoritos().forEach(meteorito -> {
            if(meteorito.getPosX() < 0){
                meteorito.remove();
            }else if(meteorito.getPosX() > stage.getWidth()){
                meteorito.remove();
            }
            if(meteorito.getPosY() < 0){
                meteorito.remove();
            }else if(meteorito.getPosY() > stage.getHeight()){
                meteorito.remove();
            }
        });
    }

    private boolean checkCollisionNaves() {
        if (navesRecived != null){
            for (NaveToRecive naveRecived: navesRecived) {
                if(this.nave.getId() != naveRecived.getIdNave()) {
                    Rectangle naveRecibida = new Rectangle(
                            (int) naveRecived.getNavePosX(),
                            (int) naveRecived.getNavePosY(),
                            (int) imagenOtrasNaves.get(naveRecived.getIdNave()).getImage().getWidth(),
                            (int) imagenOtrasNaves.get(naveRecived.getIdNave()).getImage().getHeight()
                    );
                    Rectangle naveLocal = new Rectangle(
                            (int) nave.getPosX(),
                            (int) nave.getPosY(),
                            (int) nave.getImgNave().getImage().getWidth(),
                            (int) nave.getImgNave().getImage().getHeight()
                    );
                    if(naveLocal.intersects(naveRecibida)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkCollisionBala() {
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