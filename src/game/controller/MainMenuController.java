package game.controller;

import StatVars.Resoluciones;
import game.Setter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainMenuController extends Setter {

    @FXML public void playGame() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, Resoluciones.GAME_SCREEN_WIDTH, Resoluciones.GAME_SCREEN_HEIGHT);

        GameController gameController = loader.getController();
        gameController.setScene(scene);
        gameController.setStage(stage);

        stage.setTitle("Apolo X");
        stage.setScene(scene);
        stage.show();
    }

//    Media song =new Media("ss");
//    MediaPlayer mediaPlayer = new MediaPlayer(song);
}
