package game;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Setter {
    protected Scene scene;
    protected Stage stage;
    protected int idNave;
    protected String ipServer;
    public void setScene(Scene scene){
        this.scene = scene;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setIdNave(int idNave){
        this.idNave = idNave;
    }
    public void setIpServer(String ipServer) { this.ipServer = ipServer; }

}
