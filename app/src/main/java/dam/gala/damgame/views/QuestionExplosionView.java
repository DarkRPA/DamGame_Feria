package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import dam.gala.damgame.scenes.Scene;

/**
 * Vista para la animación de explosión que se produce al capturar una pregunta
 */
public class QuestionExplosionView {
    private int spriteWidth;
    private int spriteHeight;
    public float xCoor, yCoor;
    private GameView gameView;
    private Bitmap questionExplosionBitmap;
    private boolean finished;
    private Scene scene;
    private int spriteIndex;

    /**
     * Constructor con referencia al coreógrafo o bucle de juego
     * @param gameView SurfaceView que controla el bucle de juego
     */
    public QuestionExplosionView(GameView gameView){
        this.gameView = gameView;
        this.scene = this.gameView.getScene();
        this.spriteWidth = scene.getQuesExplosionViewWidth()/scene.getQuesExplosionViewImgNumber();
        this.spriteHeight = scene.getQuesExplosionViewHeight();
        this.questionExplosionBitmap = scene.getQuesExplosionViewBitmap();
        this.spriteIndex = -1; //recien creado
    }
    /**
     * Se actualiza el estado de animación de explosión
     */
    public void updateState(){
        if(isFinished()) return;
        //incrementamos el índice del sprite al siguiente momento de la explosión
        this.spriteIndex++;
    }

    /**
     * Se dibuja la explosión
     * @param canvas Lienzo donde se dibuja la explosión
     * @param paint Estilo y color con el que se dibuja
     */
    public void draw(Canvas canvas, Paint paint){
        if(!isFinished()) {
            this.yCoor = this.gameView.getBouncyView().getyCurrentCoord();
            this.xCoor = this.gameView.getBouncyView().getxCoord();
            //Calculamos el cuadrado del sprite que vamos a dibujar
            Rect startRect = new Rect(this.spriteIndex* this.spriteWidth,
                    0, (this.spriteIndex * this.spriteWidth) + this.spriteWidth,
                    this.spriteHeight);

            //calculamos donde vamos a dibujar la porcion del sprite
            Rect endRect = new Rect((int) this.xCoor,(int) this.yCoor,
                    (int) this.xCoor + this.spriteWidth,
                    (int) this.yCoor +this.spriteHeight);

            canvas.drawBitmap(this.questionExplosionBitmap,startRect,endRect,paint);
        }

    }

    /**
     * Comprueba el estado de la animación de explosión
     * @return Devuelve true si se han recorrido todas las imágenes del sprite de explosión
     */
    public boolean isFinished(){
        this.finished = this.spriteIndex >= this.gameView.getScene().getQuesExplosionViewImgNumber();
        return finished;
    }
    public Bitmap getQuestionExplosionBitmap(){
        return this.questionExplosionBitmap;
    }
}
