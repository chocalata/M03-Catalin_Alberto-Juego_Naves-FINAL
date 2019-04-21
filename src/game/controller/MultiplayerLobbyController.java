package game.controller;

import statVars.Strings;
import game.SceneStageSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.DatagramPacket;

public class MultiplayerLobbyController extends SceneStageSetter {

    private DatagramPacket paket;

    public void playGameServer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        GameController gameController = loader.getController();
        gameController.beforeStartGame(stage, scene, paket);
        gameController.start(true);

        stage.setTitle(Strings.NOMBRE_JUEGO);
        stage.setScene(scene);
        stage.show();
    }

    public void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/multiplayerLobby.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setScene(scene);
        mainMenuController.setStage(stage);

        stage.setTitle("Apolo X");
        stage.setScene(scene);
        stage.show();
    }

    public void setPaket(DatagramPacket paket) {
        this.paket = paket;
    }
}
