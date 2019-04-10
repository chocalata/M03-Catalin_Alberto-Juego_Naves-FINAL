package game.controller;

import StatVars.Resoluciones;
import game.Setter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MultiplayerMenuController extends Setter {

    @FXML
    TextField et_ipServer;
    public void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/mainMenu.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, Resoluciones.MENU_SCREEN_WIDTH, Resoluciones.MENU_SCREEN_HEIGHT);

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setScene(scene);
        mainMenuController.setStage(stage);


        stage.setTitle("Apolo X");
        stage.setScene(scene);
        stage.show();
    }

    public void connectToServer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/multiplayerLobby.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, Resoluciones.MENU_SCREEN_WIDTH, Resoluciones.MENU_SCREEN_HEIGHT);

        MultiplayerLobbyController multiplayerLobbyController = loader.getController();
        multiplayerLobbyController.setScene(scene);
        multiplayerLobbyController.setStage(stage);

        setIpServer(et_ipServer.getText());

        stage.setTitle("Apolo X");
        stage.setScene(scene);
        stage.show();
    }
}
