package game.model;

public class Nave {
    private Cursor orientation;

    private int posX;
    private int posY;
    public final int SPEED = 3;

    public Nave(int posX, int posY) {
        orientation = new Cursor();
        this.posX = posX;
        this.posY = posY;
    }

    public Cursor getOrientation() {
        return orientation;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int pos){
        posX = pos;
    }
    public void setPosY(int pos){
        posY = pos;
    }

    public void mover(){
        //Movimiento de la nave
    }
}
