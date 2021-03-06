package game.controller;

import statVars.Strings;
import game.SceneStageSetter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainMenuController extends SceneStageSetter {
    @FXML public void playGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
            Parent root = loader.load();

            scene = new Scene(root, stage.getWidth(), stage.getHeight());

            GameController gameController = loader.getController();
            gameController.beforeStartGame(stage,scene, null);
            gameController.start(false);

            stage.setTitle(Strings.NOMBRE_JUEGO);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML public void openMultiplayer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/multiplayerMenu.fxml"));
            Parent root = loader.load();

            scene = new Scene(root, stage.getWidth(), stage.getHeight());

            MultiplayerMenuController multiplayerMenuController = loader.getController();
            multiplayerMenuController.setScene(scene);
            multiplayerMenuController.setStage(stage);


            stage.setTitle(Strings.NOMBRE_JUEGO);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML public void closeGame() {
        Platform.exit();
    }
}
