package game.services;

import StatVars.Enums;
import game.model.Meteorito;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class MeteorService {

    ArrayList<Meteorito> meteoritos = new ArrayList<>();

    final int NORTH = 0,EAST = 1, SOUTH = 2, WEST = 3;

    private double screenWidth;
    private double screenHeight;
    private GraphicsContext graphicsContext;

    public MeteorService(double screenWidth, double screenHeight, GraphicsContext graphicsContext){
        this.graphicsContext=graphicsContext;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void create(double xNave, double yNave) {

        double posX = (int) (Math.random() * screenWidth);
        double posY = (int) (Math.random() *screenHeight);

        switch ((int)(Math.random()*4)) {

            case NORTH: posY=0; break;
            case EAST: posX = screenWidth; break;
            case SOUTH: posY = screenHeight; break;
            default: posY = 0;
        }

       meteoritos.add(new Meteorito(posX,posY, xNave, yNave, graphicsContext));
    }

    public void update() {
        removeMeteor();
        if (!meteoritos.isEmpty()){
            meteoritos.forEach(Meteorito::update);
        }

    }

    public void render(){
        if (!meteoritos.isEmpty()) meteoritos.forEach(Meteorito::render);
    }

    private void removeMeteor(){
        ArrayList<Meteorito> meteorToRemove = new ArrayList<>();
        meteoritos.forEach(meteorito -> {
            if(meteorito.getState() == Enums.MeteorState.TO_REMOVE) {
                if(!meteorToRemove.contains(meteorito)) {
                    meteorToRemove.add(meteorito);
                }
            }
        });
        meteorToRemove.forEach(meteorito -> meteoritos.remove(meteorito));
    }

    public ArrayList<Meteorito> getMeteoritos() {
        return meteoritos;
    }
}
