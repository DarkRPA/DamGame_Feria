package dam.gala.damgame.views;

import androidx.annotation.NonNull;

import dam.gala.damgame.model.Play;

/**
 * Bloque de choque de la parte inferior de la escena
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class DownCrashView extends CrashView{
    private TopCrashView topCrashView;

    /**
     * Construye el bloque de choque de la parte inferior
     * @param gameView Jugada en la que se construye el bloque
     * @param topCrashView Bloque de choque de la parte superior
     */
    public DownCrashView(@NonNull GameView gameView,
                         @NonNull TopCrashView topCrashView) {
        super(gameView);
        this.topCrashView = topCrashView;
        this.setyCoor((int) (this.topCrashView.getHeight()+this.topCrashView.getyCoor())+
                        this.getScene().getBouncyViewHeight()+
                        this.getCrashBlockGap());
    }
}
