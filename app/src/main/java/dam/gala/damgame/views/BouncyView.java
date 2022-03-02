package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import dam.gala.damgame.model.GameConfig;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.scenes.Scene;

import java.util.Iterator;

/**
 * Personaje principal, debe sortear obstáculos y responder el máximo número de preguntas
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class BouncyView {
    private int spriteWidth;
    private int spriteHeight;
    public float xCoord, yCoord, yCurrentCoord;
    private int spriteIndex;
    private Bitmap bouncyBitmap;
    private float gravity;
    private boolean landed;
    private boolean collision;
    private boolean questionCatched;
    private GameView gameView;
    private GameConfig gameConfig;
    private QuestionView questionViewCatched;
    private Play play;
    private Scene scene;

    /**
     * Construye una instancia referenciando el gestor del bucle de juego
     * @param gameView Vista principal y gestor del bucle de juego
     */
    public BouncyView(GameView gameView) {
        this.gameView = gameView;
        this.play = gameView.getPlay();
        this.scene = this.gameView.getScene();
        gameConfig = gameView.getGameActivity().getGameConfig();
        this.yCoord = scene.getScreenHeight() / 2 - scene.getBouncyViewHeight() / 2;
        this.xCoord = scene.getBouncyViewWidth() / 5;
        this.yCurrentCoord = yCoord;
        this.spriteWidth = scene.getBouncyViewWidth() / scene.getBouncyViewImgNumber();
        this.spriteHeight = scene.getBouncyViewHeight();
        this.bouncyBitmap = scene.getBouncyViewBitmap();
        this.gravity = scene.getScreenWidth() * this.gameConfig.getGravity()/1920;
        spriteIndex = -1; //recien creado
    }

    /**
     * Actualiza el estado del bouncy
     */
    public void updateState() {
        if(this.isFinished())
            return;

        // 440

        if (gravity > (this.yCoord) || gravity <= -450) {
            this.gravity = 0;
            this.spriteIndex = -1;
            this.landed = true;
            this.play.setLifes(this.play.getLifes()-1);
            //this.reStart();
        } else {
            if (this.spriteIndex == 3)
                this.spriteIndex = -1;
        }

        if(!this.landed) {
            for (CrashView crashView : this.gameView.getPlay().getCrashViews()) {
               /* if (this.xCoord + this.spriteHeight >= crashView.getxCoor()) {
                    this.collision = true;
                    this.questionCatched = true;
                    //this.play.setLifes(this.play.getLifes() - 1);
                    //this.reStart();
                    break;
                }*/
            }

            Iterator iterator = this.gameView.getPlay().getQuestionViews().iterator();
            QuestionView questionView;

            while (iterator.hasNext() && !this.isFinished()) {
                questionView = (QuestionView) iterator.next();
                if (this.xCoord + this.spriteWidth >= questionView.getxCoor()
                        && this.yCoord + this.spriteHeight >= questionView.getyCoor()) {
                    this.questionCatched = true;
                    questionView.setQuestionCatched(true);
                    this.questionViewCatched = questionView;
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Dibuja el bouncy en el canvas
     * @param canvas Lienzo de digujo
     * @param paint Pincel para dibujar
     */
    public void draw(Canvas canvas, Paint paint) {
        //terminará la animación de movimiento cuando se capture una pregunta
        //, se choque con un obstáculo o se caiga al suelo
        if (isFinished())
            return;

        this.spriteIndex++;
        //se calcula el area del bitmap que se va a dibujar
        Rect startRect = new Rect(this.spriteIndex * this.spriteWidth, 0,
                (this.spriteIndex + 1) * this.spriteWidth
                , this.spriteHeight);
        Rect endRect = new Rect((int)this.xCoord, (int)(this.yCoord + this.gravity), (int)(this.xCoord +
                this.spriteWidth)
                , (int)(this.yCoord + this.spriteHeight + this.gravity));
        canvas.drawBitmap(this.getBouncyBitmap(), startRect, endRect, paint);
        //comentar las líneas de abajo para quitar la gravedad
        this.gravity+=this.gameConfig.getGravity();
        this.yCurrentCoord = this.yCoord + this.gravity;
    }

    /**
     * Tererminará la animación de movimiento cuando se capture una pregunta
     * o se choque con un obstáculo
     *
     * @return boolean Valor true significa que se debe detener la animación
     */
    public boolean isFinished() {
        if(this.play.getLifes()==0){
            return true;
        }
        return false;
    }

    public float getxCoord() {
        return this.xCoord;
    }

    public float getyCoord() {
        return this.yCoord;
    }

    public float getyCurrentCoord(){
        return this.yCurrentCoord;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public Bitmap getBouncyBitmap() {
        return this.bouncyBitmap;
    }

    public boolean isLanded() {
        return this.landed;
    }
    public boolean isCrashed(){
        return this.collision;
    }
    public boolean isQuestionCatched(){
        return this.questionCatched;
    }
    public QuestionView getQuestionCatched(){
        return this.questionViewCatched;
    }
    public void setQuestionViewCatched(QuestionView questionViewCatched){
        this.questionViewCatched = questionViewCatched;
    }

    /**
     * Reinicia el juego
     */
    public void reStart(){
        this.play.setHasWonLife(false);
        this.gravity = 0;
        this.spriteIndex = -1;
        this.collision=false;
        this.landed = false;
        this.questionCatched = false;
        this.yCoord = this.scene.getScreenHeight() / 2 - this.scene.getBouncyViewHeight() / 2;
        this.xCoord = this.scene.getBouncyViewWidth() / 5;
    }
}
