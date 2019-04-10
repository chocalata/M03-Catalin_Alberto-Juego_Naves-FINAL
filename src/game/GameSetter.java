package game;

import game.model.Nave;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameSetter extends SceneStageSetter {
    protected GraphicsContext graphicsContext;

    //Si se ha pulsado alguna.
    protected BooleanBinding anyPressed;

    //Teclas a pulsar
    protected BooleanProperty leftPressed, rightPressed, upPressed, downPressed;

    protected Nave nave;

    protected String ipServer;

    public void beforeStartGame(Stage stage, Scene scene, int idNave, String ipServer) {
        setScene(scene);

        setStage(stage);

        initControlPress();

        setNave(new Nave(graphicsContext,500,500, idNave, new ImageView("game/img/naves/navePlayer_" + idNave + ".png"), this.upPressed, this.downPressed, this.rightPressed, this.leftPressed, this.anyPressed));

        setControls();

        if(ipServer != null) {
            setIpServer(ipServer);
        }
    }

    private void initControlPress(){
        rightPressed = new SimpleBooleanProperty();
        leftPressed = new SimpleBooleanProperty();
        upPressed = new SimpleBooleanProperty();
        downPressed = new SimpleBooleanProperty();
        anyPressed = upPressed.or(downPressed).or(leftPressed).or(rightPressed);
    }

    private void setNave(Nave nave){
        this.nave = nave;
        nave.setImagenRotada(nave.getImgNave().getImage());
    }

    private void setIpServer(String ipServer) { this.ipServer = ipServer; }

    private void setControls() {
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
    }
}
