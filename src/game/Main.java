package game;

import game.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mainMenu.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
            MainMenuController controller = loader.getController();
            controller.setStage(primaryStage);

            primaryStage.setTitle("Apolo X");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
