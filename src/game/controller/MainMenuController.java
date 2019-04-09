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

        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        GameController gameController = loader.getController();
        gameController.setScene(scene);
        gameController.setStage(stage);
        gameController.setIdNave(1/*EL ID QUE ME DARÁ EL SERVIDOR PARA LA NAVE.*/);

        stage.setTitle("Apolo X");
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }

//    Media song =new Media("ss");
//    MediaPlayer mediaPlayer = new MediaPlayer(song);
}
