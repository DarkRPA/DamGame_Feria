package dam.gala.damgame.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.Timer;
import java.util.TimerTask;

import dam.gala.damgame.views.GameView;

/**
 * Bucle de juego, desde esta clase se controla todo el juego
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class GameLoop extends  Thread{
    // Frames por segundo deseados
    public static final int MAX_FPS = 20;
    // Máximo número de frames saltados
    private static final int MAX_FRAMES_SALTADOS = 5;
    // El periodo de frames
    private static final int TIEMPO_FRAME = 1000 / MAX_FPS;
    private int framesToNewQuestion=0;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;
    private boolean running=true;
    private boolean stop;

    /**
     * Contruye el bucle principal del juego
     * @param surfaceHolder Objeto de sincronización
     * @param gameView Coreógrafo del juego
     */
    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView){
        this.gameView = gameView;
        this.surfaceHolder=surfaceHolder;
    }
    @Override
    public void run() {
        Canvas canvas;
        long tiempoComienzo;      // Tiempo en el que el ciclo comenzó
        long tiempoDiferencia;    // Tiempo de duración del ciclo el ciclo
        int tiempoDormir;     // Tiempo que el thread debe dormir (<0 si vamos mal de tiempo)
        int framesASaltar; // número de frames saltados

        tiempoDormir = 0;
        while (this.running) {
            if(this.stop) continue;
            canvas = null;
            // bloquear el canvas para que nadie más escriba en el
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    tiempoComienzo = System.currentTimeMillis();
                    framesASaltar = 0; // resetear los frames saltados

                    // Por cada ciclo de ejecución: Actualizar estado del juego
                    this.gameView.updateState();

                    // renderizar la imagen
                    this.gameView.render(canvas);
                    // Calcular cuánto tardó el ciclo
                    tiempoDiferencia = System.currentTimeMillis() - tiempoComienzo;
                    // Calcular cuánto debe dormir el thread antes de la siguiente iteración
                    tiempoDormir = (int)(TIEMPO_FRAME - tiempoDiferencia);

                    if (tiempoDormir > 0) {
                        // si sleepTime > 0 vamos bien de tiempo
                        try {
                            // Enviar el thread a dormir
                            // Algo de batería ahorramos
                            Thread.sleep(tiempoDormir);
                        } catch (InterruptedException e) {}
                    }

                    while (tiempoDormir < 0 && framesASaltar < MAX_FRAMES_SALTADOS) {
                        // Vamos mal de tiempo: Necesitamos ponernos al día
                        gameView.updateState(); // actualizar si rendering
                        tiempoDormir += TIEMPO_FRAME;  // actualizar el tiempo de dormir
                        framesASaltar++;
                    }
                }
            } finally {
                // si hay excepción desbloqueamos el canvas
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void endGame(){
        long now = System.currentTimeMillis();
        //running timer task as daemon thread
        Timer timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameLoop.this.running=false;
            }
        },3000);
    }

    public void stopGame(boolean pause){
        this.stop = pause;
    }
}
