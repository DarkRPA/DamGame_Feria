package dam.gala.damgame.controllers;

import android.media.MediaPlayer;
import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.scenes.Scene;

/**
 * Controlador de audio
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class AudioController {
    private GameActivity gameActivity;
    private MediaPlayer mediaPlay, mediaEndGame;
    private Scene scene;
    private boolean audioExplosionStarted;
    private boolean audioQuestionCatchedStarted;
    private boolean audioEndGameStarted;

    /**
     * Controlador de audio
     * @param gameActivity Actividad principal del juego
     */
    public AudioController(GameActivity gameActivity){
        this.gameActivity = gameActivity;
        this.scene = this.gameActivity.getPlay().getScene();
    }

    /**
     * Inicio de reproducción de audio del sonido principal del juego
     */
    public void startSceneAudioPlay(){
        this.mediaPlay = MediaPlayer.create(gameActivity,this.scene.getAudioPlay());
        this.mediaPlay.setLooping(true);
        this.mediaPlay.start();
    }
    /**
     * Detiene la reproducción de audio del sonido principal del juego
     */
    public void stopAudioPlay(){
        this.mediaPlay.stop();
    }
    /**
     * Inicio de la reproducción de audio del sonido correspondiente a una explosion
     */
    public void startAudioExplosion(){
        MediaPlayer mediaExplosion = MediaPlayer.create(gameActivity,this.scene.getAudioExplosion());
        mediaExplosion.setVolume(80,80);
        mediaExplosion.start();
        this.audioExplosionStarted = true;
    }

    /**
     * Detiene la reproducción de sonido de la explosión
     */
    public void stopAudioExplosion(){
        this.audioExplosionStarted =false;
    }
    /**
     * Inicio de la reproducción de audio del sonido correspondiente a la captura de una
     * pregunta.
     */
    public void startAudioQuestionCatched(){
        MediaPlayer mediaExplosion = MediaPlayer.create(gameActivity,this.scene.getAudioQuestionCatched());
        mediaExplosion.setVolume(80,80);
        mediaExplosion.start();
        this.audioExplosionStarted = true;
    }

    /**
     * Detiene el sonido de la captura de una pregunta
     */
    public void stopAudioQuestionCatched(){
        this.audioQuestionCatchedStarted = false;
    }

    /**
     * Obtiene el estado de la reproducción de sonido de la explosión
     * @return Devuelve true si la reproducción del sonido de la explosión se ha iniciado
     */
    public boolean isAudioExplosionStarted(){
        return this.audioExplosionStarted;
    }

    public void startAudioEndGame(){
        this.mediaPlay.stop();
        this.mediaEndGame = MediaPlayer.create(gameActivity,this.scene.getAudioEndGame());
        this.mediaEndGame.setVolume(120,120);
        this.mediaEndGame.start();
        this.mediaEndGame.setLooping(true);
        this.audioEndGameStarted = true;
    }

    public boolean isAudioEndGameStarted(){
        return this.audioEndGameStarted;
    }

    public void stopAudioEndGame(){
        this.audioEndGameStarted=false;
    }
}
