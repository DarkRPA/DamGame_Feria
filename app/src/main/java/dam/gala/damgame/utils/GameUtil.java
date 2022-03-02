package dam.gala.damgame.utils;

import android.view.View;

/**
 * Utiles con características inmutables
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public abstract class GameUtil {
    public static final int PREGUNTA_COMPLEJIDAD_BAJA=0; //puntos
    public static final int PREGUNTA_COMPLEJIDAD_ALTA=1; //puntos o 1 vida
    public static final int PREGUNTA_SIMPLE=10; //radibuttons
    public static final int PREGUNTA_MULTIPLE=20; //checkboxes
    public static final int PREGUNTA_LISTA=30; //spinner
    public static final int TEMA_CIUDAD=100;
    public static final int TEMA_DESIERTO=101;
    public static final int TEMA_ESPACIO=102;
    public static final int TEMA_HIELO=103;
    public static final int TEMA_SELVA=104;;
    public static final int TEMA_SUBMARINO=105;
    public static final int TEMA_VOLCANES=106;
    public static final int HIGH_COMPLEX_SPEED=5;
    public static final int LOW_COMPLEX_SPEED=4;
    public static final int POINTS_TO_CHANGE_LEVEL=2000;
}
