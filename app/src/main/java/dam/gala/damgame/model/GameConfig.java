package dam.gala.damgame.model;

import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.scenes.Scene;

/**
 * Gestiona la configuración de la aplicación
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class GameConfig {
    //questions
    private int questions=5;
    private int timeToQuestion = 60;
    private int framesToNewQuestion=0;
    //crash blocks
    private int crashBlocks= 5;
    private int timeToCrashBlock=20;
    private int framesToNewCrashBlock=0;
    private int minHeightCrashBlock =0;
    //gravity
    private int gravity=5;
    public static int ORIGINAL_GRAVITY = 5;
    private Scene scene;

    public GameConfig(Scene scene){
        this.scene = scene;
        this.crashBlocks = scene.getScreenWidth()/(2*scene.getCrashViewWidth());
        this.minHeightCrashBlock = scene.getBouncyViewHeight()/2;
    }

    /* Métodos getter y setters para propiedades de configuración del juego */

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public int getTimeToQuestion() {
        return timeToQuestion;
    }

    public void setTimeToQuestion(int timeToQuestion) {
        this.timeToQuestion = timeToQuestion;
    }

    public int getFramesToNewQuestion() {
        return framesToNewQuestion;
    }

    public void setFramesToNewQuestion(int framesToNewQuestion) {
        this.framesToNewQuestion = framesToNewQuestion;
    }

    public int getFramesToNewCrashBlock() {
        return framesToNewCrashBlock;
    }

    public void setFramesToNewCrashBlock(int framesToNewCrashBlock) {
        this.framesToNewCrashBlock = framesToNewCrashBlock;
    }

    public int getTimeToCrashBlock() {
        return timeToCrashBlock;
    }

    public void setTimeToCrashBlock(int timeToCrashBlock) {
        this.timeToCrashBlock = timeToCrashBlock;
    }

    public int getCrashBlocks() {
        return crashBlocks;
    }

    public void setCrashBlocks(int crashBlocks) {
        this.crashBlocks = crashBlocks;
    }

    public int getMinHeightCrashBlock() {
        return minHeightCrashBlock;
    }

    public void setMinHeightCrashBlock(int minHeightCrashBlock) {
        this.minHeightCrashBlock = minHeightCrashBlock;
    }

    public int getGravity(){
        return this.gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}
