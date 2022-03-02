package dam.gala.damgame.views;

import androidx.annotation.NonNull;
import dam.gala.damgame.model.Play;
import java.util.Random;
/**
 * Bloque de choque de la parte superior de la escena
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class TopCrashView extends CrashView{
    /**
     * Construye el bloque de choque de la parte superior
     * @param gameView Jugada en la que se construye el bloque de choque
     */
    //la altura del bloque se generará aleatoriamente. Los valores oscilarán entre
    //la altura de la pantalla menos la altura del OGP, menos la altura mínima del bloque
    //, menos el hueco
    public TopCrashView(@NonNull GameView gameView) {
        super(gameView);
        Random random = new Random();
        int max, min;

        max = this.getScene().getScreenHeight()-(this.getScene().getBouncyViewHeight()
                + this.getCrashBlockGap());


        min = this.getMinHeight();

        this.setyCoor(-(this.getHeight()-(random.nextInt((max - min) ) + min)));
    }
}
