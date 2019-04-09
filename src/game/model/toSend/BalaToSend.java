package game.model.toSend;

public class BalaToSend {

    public BalaToSend(double posX, double posY, int idNave){
        this.posX = posX;
        this.posY = posY;

        this.idNave = idNave;
}
    private double posX;
    private double posY;

    private int idNave;

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
