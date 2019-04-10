package game.controller;

import StatVars.Resoluciones;
import StatVars.Strings;
import game.GameSetter;
import game.SceneStageSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MultiplayerLobbyController extends SceneStageSetter {

    public void playGameServer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, Resoluciones.MENU_SCREEN_WIDTH, Resoluciones.MENU_SCREEN_HEIGHT);

        //enviar Paquete Al Servidor Para Conectarnos;

        //recibimos El Paquete Del Servidor Y Asignamos El Id A la nave

        //esperar a que el servidor nos diga que ha comenzado el juego

        GameController gameController = loader.getController();
        //gameController.beforeStartGame(stage, scene, IPQUEHASRECIBIDODELSERVIDOR, ipServer);

        stage.setTitle(Strings.NOMBRE_JUEGO);
        stage.setScene(scene);
        stage.show();
    }

    public void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/multiplayerLobby.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, Resoluciones.MENU_SCREEN_WIDTH, Resoluciones.MENU_SCREEN_HEIGHT);

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setScene(scene);
        mainMenuController.setStage(stage);

        stage.setTitle("Apolo X");
        stage.setScene(scene);
        stage.show();
    }
}
