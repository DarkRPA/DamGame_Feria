package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import dam.gala.damgame.model.Play;
import dam.gala.damgame.scenes.Scene;

/**
 * Bloque de choque
 */
public class CrashView {
    private int minHeight;
    private int height;
    private int width;
    private float xCoor, yCoor;
    private Bitmap crashBitmap;
    private Scene scene;
    private Play play;
    private int crashBlockGap;
    private float horizontalSpeed;

    /**
     * Construye bloques de choque en la jugada indicada
     * @param gameView Jugada en la que se construye el bloque de choque
     */
    public CrashView(GameView gameView){
        this.play = gameView.getPlay();
        this.scene = play.getScene();
        this.height = this.scene.getCrashViewHeight();
        this.width = this.scene.getCrashViewWidth();
        this.minHeight = this.play.getConfig().getMinHeightCrashBlock();
        this.crashBitmap = (this instanceof TopCrashView)?
                this.getScene().getCrashViewBitmapTop():
                this.getScene().getCrashViewBitmapDown();
        this.xCoor = this.scene.getScreenWidth();
        this.crashBlockGap = (int)(1.6*gameView.getBouncyView().getBouncyBitmap().getHeight());
        this.horizontalSpeed = this.scene.getScreenWidth() *10/1920;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(this.crashBitmap
                , this.getxCoor(), this.getyCoor(), paint);
    }
    /**
     * Actualización del estado del blqque de choque
     */
    public void updateState(){
        //se calcula la separación horizontal de los bloques
        if(this.getxCoor()<=-this.getWidth())
            this.setxCoor(this.getScene().getScreenWidth());
        else
            this.setxCoor(this.getxCoor() - this.horizontalSpeed);
    }
    //-----------------------------------------------------------------------------------------
    //Métodos getter y setter para propiedades de los bloques de choque
    //-----------------------------------------------------------------------------------------
    protected int getCrashBlockGap() {
        return crashBlockGap;
    }
    protected int getHeight() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected int getWidth() {
        return width;
    }

    protected int getMinHeight() {
        return minHeight;
    }

    protected void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected float getxCoor() {
        return xCoor;
    }

    protected void setxCoor(float xCoor) {
        this.xCoor = xCoor;
    }

    protected float getyCoor() {
        return yCoor;
    }

    protected void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }

    protected Bitmap getCrashBitmap() {
        return crashBitmap;
    }

    protected void setCrashBitmap(Bitmap crashBitmap) {
        this.crashBitmap = crashBitmap;
    }

    protected Scene getScene() {
        return scene;
    }

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    protected Play getPlay() {
        return play;
    }

    protected void setPlay(Play play) {
        this.play = play;
    }
}
