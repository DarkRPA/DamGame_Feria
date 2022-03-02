package dam.gala.damgame.scenes;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import dam.gala.damgame.activities.GameActivity;

/**
 * Scena genérica, derivan de esta clase el resto de escenas específicas
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public abstract class Scene {
    //scene
    private int screenWidth=0;
    private int screenHeight=0;
    protected int backgroundScenes[];
    protected Scene scene;
    //question
    private int questionViewWidth =100;
    private int questionViewHeight =100;
    private Bitmap questionViewBitmap;
    private int questionViewImgNumber =4;
    //bouncy
    private int bouncyViewWidth =100;
    private int bouncyViewHeight =100;
    private Bitmap bouncyViewBitmap;
    private int bouncyBitmapId;
    private int bouncyViewImgNumber =4;
    //explosion
    private int explosionViewWidth=100;
    private int explosionViewHeight=100;
    private Bitmap explosionViewBitmap;
    private int explosionBitmapId;
    private int explosionViewImgNumber=16;
    //question catched
    private int quesExplosionViewWidth=100;
    private int quesExplosionViewHeight=100;
    private Bitmap quesExplosionViewBitmap;
    private int quesExplosionBitmapId;
    private int quesExplosionViewImgNumber=16;
    //crash block
    private int crashViewWidth=100;
    private int crashViewHeight =100;
    private Bitmap crashViewBitmapTop;
    private Bitmap crashViewBitmapDown;
    //image transition control
    protected int xCurrentImg=0;
    protected int xNextImg=0;
    protected int currentImgIndex=0;
    protected int nextImgIndex=0;
    //image for score
    private Bitmap scoreLifes;
    private Bitmap scorePoints;
    private Bitmap scoreAnswers;

    /**
     * Construye la escena del juego
     * @param gameActivity Actividad principal del juego
     */
    public Scene(GameActivity gameActivity){
        this.getScreenSizeInlcudingTopBottomBar(gameActivity);
    }

    public abstract int getQuestionViewWidth();
    public abstract int getQuestionViewHeight();
    public abstract Bitmap getQuestionViewBitmap(int complexity);
    public abstract int getQuestionViewImgNumber();
    public abstract int getBouncyViewWidth();
    public abstract int getBouncyViewHeight();
    public abstract Bitmap getBouncyViewBitmap();
    public abstract int getBouncyViewImgNumber();
    public abstract int getBouncyBitmapId();
    public abstract int[] getBackgroundScenes();
    public abstract int getxCurrentImg();
    public abstract int getxNextImg();
    public abstract void setxCurrentImg(int xCurrentImg);
    public abstract void setxNextImg(int xNextImg);
    public abstract int getCurrentImgIndex();
    public abstract void setCurrentImgIndex(int currentImgIndex);
    public abstract int getNextImgIndex();
    public abstract void setNextImgIndex(int nextImgIndex);
    public abstract int getExplosionViewWidth();
    public abstract int getExplosionViewHeight();
    public abstract int getExplosionBitmapId();
    public abstract int getExplosionViewImgNumber();
    public abstract Bitmap getExplosionViewBitmap();
    public abstract View getCharacterView();
    public abstract int getAudioPlay();
    public abstract int getAudioExplosion();
    public abstract int getAudioCrash();
    public abstract int getAudioEndGame();

    public abstract int getCrashViewWidth();
    public abstract int getCrashViewHeight();
    public abstract Bitmap getCrashViewBitmapTop();
    public abstract Bitmap getCrashViewBitmapDown();
    public abstract int getQuesExplosionViewWidth();
    public abstract int getQuesExplosionViewHeight();
    public abstract int getQuesExplosionBitmapId();
    public abstract int getQuesExplosionViewImgNumber();
    public abstract Bitmap getQuesExplosionViewBitmap();
    public abstract int getAudioQuestionCatched();
    public abstract Bitmap getScoreLifes();
    public abstract Bitmap getScorePoints();
    public abstract Bitmap getScoreAnswers();

    /**
     * Obtiene el tamaño de la pantalla
     * @param gameActivity Actividad principal del juego
     */
    public void getScreenSize(GameActivity gameActivity){
        if(Build.VERSION.SDK_INT > 13) {
            Display display = gameActivity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        }
        else{
            Display display = gameActivity.getDisplay();
            screenWidth = display.getWidth();  // deprecated
            screenHeight = display.getHeight();  // deprecated
        }
    }

    //  Get Screen width & height including top & bottom navigation bar
    public int[] getScreenSizeInlcudingTopBottomBar(Context context) {
        int [] screenDimensions = new int[2]; // width[0], height[1]
        int x, y, orientation = context.getResources().getConfiguration().orientation;
        WindowManager wm = ((WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE));
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point screenSize = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(screenSize);
                x = screenSize.x;
                y = screenSize.y;
            } else {
                display.getSize(screenSize);
                x = screenSize.x;
                y = screenSize.y;
            }
        } else {
            x = display.getWidth();
            y = display.getHeight();
        }

        screenDimensions[0] = orientation == Configuration.ORIENTATION_LANDSCAPE ? x : y; // width
        screenDimensions[1] = orientation == Configuration.ORIENTATION_LANDSCAPE ? y : x; // height

        this.screenWidth = screenDimensions[0];
        this.screenHeight = screenDimensions[1];

        return screenDimensions;
    }
    /**
     * Obtiene el ancho de la pantalla
     */
    public int getScreenWidth(){
        return this.screenWidth;
    }
    /**
     * Obtiene el ancho de la pantalla
     */
    public int getScreenHeight(){
        return this.screenHeight;
    }
}
