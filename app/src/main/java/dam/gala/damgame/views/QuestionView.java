package dam.gala.damgame.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import dam.gala.damgame.data.DatabaseManager;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.scenes.Scene;
import dam.gala.damgame.utils.GameUtil;

import java.util.List;
import java.util.Random;

/**
 * Vista gráfica del objeto de una pregunta
 *
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class QuestionView {
    private int spriteWidth;
    private int spriteHeight;
    private int xCoor, yCoor;
    private int spriteIndex;
    private Play play;
    private Scene scene;
    private Bitmap questionBitmap;
    private Question question;
    private int verticalDirection;
    private int horizontalDirection;
    private int speed;
    private boolean questionCatched;

    /**
     * Construye la vista de una pregunta
     * @param play Jugada en la que se construye la vista gráfica de una pregunta
     * @param question Pregunta relacionada con la vista gráfica
     */
    public QuestionView(@NonNull Play play, @NonNull Question question) {
        Random random;
        float randomCoor;
        this.play = play;
        this.scene = play.getScene();
        this.question = question;
        spriteWidth = this.scene.getQuestionViewWidth()/this.scene.getQuestionViewImgNumber();
        spriteHeight =this.scene.getQuestionViewHeight();
        spriteIndex =-1;

        this.questionBitmap = play.getScene().getQuestionViewBitmap(question.getDifficulty());
        //el siguiente código hay que modificarlo cuando tengamos las preguntas
        random = new Random();

        if(random.nextFloat()<0.20 && question.getDifficulty() == GameUtil.PREGUNTA_COMPLEJIDAD_ALTA){
            this.speed =  GameUtil.HIGH_COMPLEX_SPEED * this.scene.getScreenWidth() /1920;
            System.out.println(question);
            this.questionBitmap = this.scene.getQuestionViewBitmap(GameUtil.PREGUNTA_COMPLEJIDAD_ALTA);
        }else{
            this.speed = GameUtil.LOW_COMPLEX_SPEED * this.scene.getScreenWidth() /1920;
            this.questionBitmap = this.scene.getQuestionViewBitmap(GameUtil.PREGUNTA_COMPLEJIDAD_BAJA);
        }

        //cálculo de dirección aleatoria de cada pregunta generada
        if(random.nextFloat()>0.5)
            this.horizontalDirection = 1;
        else
            this.horizontalDirection = -1;

        if(random.nextFloat()>0.5)
            this.verticalDirection = 1;
        else
            this.verticalDirection = -1;

        /* Posicionamiento de la pregunta */
        //entre 0,66y 0.999 sale por arriba (y=0, x=aleatorio(1/5) alto pantalla)
        //entre 0.33 y 0.66 sale por abajo (y=AltoPantalla-altobitmap, x=aleatorio(1/5))
        //< 0,33 sale por el centro (x=0, y=aleatorio entre 0
        // y AltoPantalla-AltoBitmap)
        randomCoor = random.nextFloat();
        if (randomCoor>=0.33) {
            //probabilidad de que la pregunta salga por los arriba o abajo
            if (randomCoor > 0.66) //sale por la arriba
                this.yCoor =0;
            else
                this.yCoor = this.scene.getScreenHeight() -
                        this.spriteHeight;
        } else {
            this.yCoor = (int) (random.nextFloat() * (this.scene.getScreenHeight()
                                - this.spriteHeight));
        }
        this.xCoor = (int) (this.scene.getScreenWidth()+ (random.nextFloat() * 10));
        //prueba para hacer que la pregunta choque con el bouncy
        /*this.yCoor = scene.getScreenHeight() / 2 - scene.getBouncyViewHeight() / 2;
        this.xCoor = scene.getScreenWidth() / 4;
        this.horizontalDirection=-1;*/
    }

    /**
     * Actualiza el estado gráfico y de animación del objeto gráfico de una pregunta
     */
    public void updatePosition(){
        //se calcula la nueva posición de la pregunta
        this.xCoor += this.horizontalDirection * this.speed;
        this.yCoor += this.verticalDirection * this.speed;
        //Cambios de direcciones al llegar a los bordes de la pantalla
        if(this.yCoor <=0 && this.verticalDirection ==-1)
            this.verticalDirection =1;
        if(this.yCoor >this.scene.getScreenHeight()-
                this.spriteHeight &&
                this.verticalDirection ==1)
            this.verticalDirection =-1;

        if(this.xCoor >=this.scene.getScreenWidth() && this.horizontalDirection ==1)
            this.horizontalDirection =-1;
        if(this.xCoor <=0 && this.horizontalDirection ==-1)
            this.horizontalDirection =1;
    }

    /**
     * Dibuja el objeto gráfico de la pregunta
     * @param canvas Lienzo donde se dibuja el objeto gráfico de la pregunta
     * @param paint Estilo y color con el que se dibuja
     */
    public void draw(Canvas canvas, Paint paint){
        int spritePosition;

        if(!isFinished()) {
            //se va incrementando el índice de imagen para producir la animación en la pregunta
            spriteIndex = spriteIndex == 3 ? 0 : spriteIndex + 1;
            spritePosition = spriteIndex * spriteWidth;
            //Calculamos el cuadrado del sprite que vamos a dibujar
            Rect origen = new Rect(spritePosition, 0, spritePosition + spriteWidth,
                    spriteHeight);

            //calculamos donde vamos a dibujar la porcion del sprite
            Rect destino = new Rect((int) this.xCoor, (int) this.yCoor,
                    (int) this.xCoor + this.spriteWidth,
                    (int) this.yCoor + this.spriteHeight);

            canvas.drawBitmap(this.questionBitmap, origen, destino, paint);
        }
    }

    /**
     * Comprueba si hay que seguir mostrando el objeto gráfico de la pregunta
     * @return
     */
    public boolean isFinished(){
        return spriteIndex >=this.scene.getQuestionViewImgNumber() || this.questionCatched;
    }
    public Question getQuestion() {
        return question;
    }
    public int getxCoor(){
        return this.xCoor;
    }
    public int getyCoor(){
        return this.yCoor;
    }

    public boolean isQuestionCatched() {
        return questionCatched;
    }

    public void setQuestionCatched(boolean questionCatched) {
        this.questionCatched = questionCatched;
    }
    public QuestionView getQuestionCatched(){
        return this;
    }
}
