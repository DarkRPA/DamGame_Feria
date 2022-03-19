package dam.gala.damgame.model;

import android.app.Activity;

import androidx.annotation.NonNull;
import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.scenes.DesertScene;
import dam.gala.damgame.utils.GameUtil;
import dam.gala.damgame.views.CrashView;
import dam.gala.damgame.scenes.JungleScene;
import dam.gala.damgame.views.QuestionView;
import dam.gala.damgame.scenes.Scene;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Jugada, recoge las principales características del juego
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class Play {
    private final int SCORE_POINTS = 10;
    private final int SCORE_LIFES = 11;
    private final int SCORE_ANSWERS = 12;
    private LocalDateTime starDateTime;
    private LocalDateTime endDateTime;
    private Scene scene;
    private Player player;
    private GameConfig config;
    private int questionsCaptured = 0;
    private int questionsCreated = 0;
    private int crashBlockCreated = 0;
    private int points;
    private int lifes;
    private int level;
    private GameActivity gameActivity;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<QuestionView> questionViews = new ArrayList<>();
    private ArrayList<CrashView> crashViews = new ArrayList<>();
    private int scoreAnswers;
    private boolean hasWonLife = false;

    /**
     * Constructor privado para la factoría
     */
    private Play(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.lifes = 3;
    }

    public boolean isHasWonLife() {
        return hasWonLife;
    }

    public void setHasWonLife(boolean hasWonLife) {
        this.hasWonLife = hasWonLife;
    }

    public int getScoreAnswers() {
        return scoreAnswers;
    }

    public void setScoreAnswers(int scoreAnswers) {
        this.scoreAnswers = scoreAnswers;

        this.updateScore(SCORE_ANSWERS, this.scoreAnswers);
    }

    /**
     * Crea la jugada a partir de la escena elgida. Es un método de factoría.
     * @param gameActivity Actividad principal del juego
     * @param sceneCode Escena elegida, código equivalente
     * @return La jugada actual del juego (Play)
     */
    public static Play createGameMove(@NonNull GameActivity gameActivity, int sceneCode) {
        Play play = new Play(gameActivity);
        play.questions = new ArrayList<>();
        switch (sceneCode) {
            case GameUtil.TEMA_DESIERTO:
                play.scene = new DesertScene(gameActivity);
                break;
            case GameUtil.TEMA_ESPACIO:
                break;
            case GameUtil.TEMA_CIUDAD:
                break;
            case GameUtil.TEMA_HIELO:
                break;
            case GameUtil.TEMA_SELVA:
                play.scene = new JungleScene(gameActivity);
                break;
            case GameUtil.TEMA_SUBMARINO:
                break;
            case GameUtil.TEMA_VOLCANES:
                break;
            default:
                play.scene = new JungleScene(gameActivity);
                break;
        }
        return play;
    }

    public static Scene getSceneByCode(GameActivity gameActivity, int sceneCode){
        switch (sceneCode) {
            case GameUtil.TEMA_DESIERTO:
                return new DesertScene(gameActivity);
            case GameUtil.TEMA_ESPACIO:
                break;
            case GameUtil.TEMA_CIUDAD:
                break;
            case GameUtil.TEMA_HIELO:
                break;
            case GameUtil.TEMA_SELVA:
                return new JungleScene(gameActivity);
            case GameUtil.TEMA_SUBMARINO:
                break;
            case GameUtil.TEMA_VOLCANES:
                break;
            default:
                return new JungleScene(gameActivity);
        }
        return null;
    }

    //-----------------------------------------------------------------------------------------
    //Métodos la escena del juego
    //-----------------------------------------------------------------------------------------
    public Scene getScene() {
        return scene;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos la creación de preguntas
    //-----------------------------------------------------------------------------------------
    public int getQuestionsCreated() {
        return questionsCreated;
    }
    public void setQuestionsCreated(int questionsCreated) {
        this.questionsCreated = questionsCreated;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos para propiedades de estadísticas de juego
    //-----------------------------------------------------------------------------------------
    public int getQuestionsCaptured() {
        return questionsCaptured;
    }
    public void setQuestionsCaptured(int questionsCaptured) {
        this.questionsCaptured = questionsCaptured;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
        this.updateScore(SCORE_POINTS, this.points);
    }
    public LocalDateTime getStarDateTime() {
        return starDateTime;
    }
    public void setStarDateTime(LocalDateTime starDateTime) {
        this.starDateTime = starDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos para configuración del juego
    //-----------------------------------------------------------------------------------------
    public GameConfig getConfig() {
        return config;
    }
    public void setConfig(GameConfig config) {
        this.config = config;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos para preguntas
    //-----------------------------------------------------------------------------------------
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }
    public ArrayList<QuestionView> getQuestionViews() {
        return questionViews;
    }

    public int getCrashBlockCreated() {
        return crashBlockCreated;
    }

    public void setCrashBlockCreated(int crashBlockCreated) {
        this.crashBlockCreated = crashBlockCreated;
    }
    public ArrayList<CrashView> getCrashViews() {
        return crashViews;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos getter y setters para la vida del OGP
    //-----------------------------------------------------------------------------------------
    public int getLifes() {
        return lifes;
    }
    public void setLifes(int lifes) {
        this.lifes = lifes;
        this.updateScore(SCORE_LIFES, this.getLifes());
    }

    //-----------------------------------------------------------------------------------------
    //Métodos getter y setters para la vida del OGP
    //-----------------------------------------------------------------------------------------
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    //-----------------------------------------------------------------------------------------
    //Estado de finalización del juego
    //-----------------------------------------------------------------------------------------
    public boolean isFinished() {
         return this.lifes == 0 || (this.scene.getNextImgIndex() < this.scene.getCurrentImgIndex()
                && this.scene.getNextImgIndex() == 0);
    }
    /**
     * Actualiza los datos de puntuación, vidas y respuestas
     * @param what  Lo que se quiere actualizar
     * @param value Valor de actualización
     */
    private void updateScore(final int what, final Object value) {
        this.gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (what) {
                    case SCORE_ANSWERS:
                        Play.this.gameActivity.updateScoreAnswer((Integer) value);
                        break;
                    case SCORE_LIFES:
                        Play.this.gameActivity.updateLifes((Integer) value);
                        break;
                    case SCORE_POINTS:
                        Play.this.gameActivity.updateScore((Integer) value);
                        break;
                }
            }
        });
    }
}
