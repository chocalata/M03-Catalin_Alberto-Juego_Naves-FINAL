package game.model.toSend;

public class BalaToSend {

    private final int idBala;
    private int idNave;

    private double posX;
    private double posY;

    public BalaToSend(double posX, double posY, int idNave, int idBala){
        this.posX = posX;
        this.posY = posY;
        this.idBala = idBala;
        this.idNave = idNave;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPos(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getIdBala() {
        return idBala;
    }

    public int getIdNave() {
        return idNave;
    }
}
