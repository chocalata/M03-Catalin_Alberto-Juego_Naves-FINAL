package game.controller;

import StatVars.Strings;
import game.SceneStageSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;

public class GameOverController extends SceneStageSetter {

    @FXML Text score;

    public void setScore(String string){
        score.setText("Score: " + string);
    }


    public void exitGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/mainMenu.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setScene(scene);
        mainMenuController.setStage(stage);

        stage.setTitle(Strings.NOMBRE_JUEGO);
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }

    public void restartGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/game.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, stage.getWidth(), stage.getHeight());

        GameController gameController = loader.getController();
        gameController.beforeStartGame(stage,scene, null);
        gameController.start(false);

        stage.setTitle(Strings.NOMBRE_JUEGO);
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }
}
