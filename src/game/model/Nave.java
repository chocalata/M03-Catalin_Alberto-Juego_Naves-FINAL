package game.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Nave {
    private int id;

    private Cursor orientation;

    private double posX;
    private double posY;

    private final int SPEED = 5;
    private int lives;
    private final int MAX_LIFES = 5;

    private BooleanProperty upPressed, downPressed, rightPressed, leftPressed;
    private BooleanBinding anyPressed;

    private ImageView imgNave;
    private Image imagenRotada;

    private Arma arma;

    private SnapshotParameters snapshotParameters;

    private GraphicsContext graphicsContext;

    private Image imgCorazonVida;


    public Nave(GraphicsContext graphicsContext, int posX, int posY, int idNave, ImageView imgNave, BooleanProperty upPressed, BooleanProperty downPressed, BooleanProperty rightPressed, BooleanProperty leftPressed, BooleanBinding anyPressed) {
        lives = 3;
        imgCorazonVida = new Image("game/res/img/vida.png");

        this.id = idNave;

        arma = new Arma(graphicsContext);
        orientation = new Cursor();

        this.graphicsContext = graphicsContext;

        this.posX = posX;
        this.posY = posY;

        this.upPressed = upPressed;
        this.downPressed = downPressed;
        this.rightPressed = rightPressed;
        this.leftPressed = leftPressed;
        this.anyPressed = anyPressed;

        this.imgNave = imgNave;

        this.snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);

    }

    public Arma getArma(){
        return arma;
    }

    public Cursor getOrientation() {
        return orientation;
    }

    public void setOrientation(double x, double y){
        orientation.setPosX(x);
        orientation.setPosY(y);
    }

    public void setImagenRotada(Image imagenRotada) {
        this.imagenRotada = imagenRotada;
    }

    public Image getImagenRotada() {
        return imagenRotada;
    }

    public ImageView getImgNave(){
        return imgNave;
    }
    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double pos){
        posX = pos;
    }
    public void setPosY(double pos){
        posY = pos;
    }

    public int getId(){
        return id;
    }

    private void mover(){
        if (upPressed.get()) {
            posY = getPosY() - SPEED;
        }
        if (downPressed.get()) {
            posY = getPosY() + SPEED;
        }
        if (leftPressed.get()) {
            posX = getPosX() - SPEED;
        }
        if (rightPressed.get()) {
            posX = getPosX() + SPEED;
        }
    }

    public double getAngle(){
//        double centerX = posX + imgNave.getHeight()/2;
//        double centerY = posY + imgNave.getWidth()/2;
//
//
//        double cc = (centerX - orientation.getPosX());
//        double co = (centerY - orientation.getPosY());
//
//        return Math.toDegrees(Math.atan2(co, cc))-90;
        return Math.round(Math.toDegrees(
                Math.atan2(
                        ((posY + imagenRotada.getWidth()/2) - orientation.getPosY()),
                        ((posX + imagenRotada.getHeight()/2) - orientation.getPosX()))
                )
        )-90;
    }

    private void rotate(){
        imgNave.setRotate(getAngle());

        imagenRotada = imgNave.snapshot(snapshotParameters, null);
    }

    public void shoot(double cursorX, double cursorY) {

        //mediaPlayer.setOnEndOfMedia(()->mediaPlayer.stop());

        arma.shoot(
                (posX + imgNave.getImage().getWidth()/2),
                (posY + imgNave.getImage().getHeight()/2),
                (posX + imgNave.getImage().getWidth()/2) - cursorX,
                (posY + imgNave.getImage().getHeight()/2) - cursorY,
                getAngle());
    }

    public void update(double time){
        arma.update(time);
        if(anyPressed.get()) {
            mover();
        }
        rotate();
    }

    public void render(){
        graphicsContext.drawImage(imagenRotada, posX, posY);

        arma.render();
        for (int i = 0; i < lives; i++) {
            graphicsContext.drawImage(imgCorazonVida, 120 + 30*i, 110);
        }
    }

    public void subsLive(){
        lives--;
    }

    public void addLive(){
        if(lives != 5) {
            lives++;
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

}
