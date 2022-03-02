package dam.gala.damgame.controllers;

import android.view.MotionEvent;
import android.view.View;
import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.model.Touch;
import java.util.ArrayList;

/**
 * Controlador para el toque de pantalla
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class TouchController implements View.OnTouchListener{
    private GameActivity gameActivity;
    private boolean touched=false;
    private ArrayList<Touch> touches = new ArrayList<Touch>();
    /**
     * Controlador del gesto de toque en la pantalla
     * @param gameActivity Actividad principal a la que se asocia el controlador
     */
    public TouchController(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }
    /**
     * Método de evento para controlar los toques en pantalla
     * @param view Origen del evento
     * @param motionEvent Objeto de evento
     * @return Devuelve true cuando el evento es gestionado, en otro caso false
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int index;
        int x,y;
        // Obtener el pointer asociado con la acción
        index = motionEvent.getActionIndex();
        x = (int) motionEvent.getX();
        y = (int) motionEvent.getY();
        switch(motionEvent.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touched=true;
                synchronized(this) {
                    touches.add(index, new Touch(index, x, y));
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                synchronized(this) {
                    touches.remove(index);
                }
                break;
            case MotionEvent.ACTION_UP:
                synchronized(this) {
                    touches.clear();
                }
                touched=false;
                break;
        }
        return true;
    }
    /**
     * Obtiene el estado del toque en pantalla
     * @return Devuelve true si se ha tocado, en caso contrario false
     */
    public boolean isTouched(){
        return this.touched;
    }
    /**
     * Obtiene la lista de toques almacenados
     * @return Devuelve una lista de toques en pantalla
     */
    public ArrayList<Touch> getTouches(){
        return this.touches;
    }
}
