package dam.gala.damgame.model;

/**
 * Toque en la pantalla
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class Touch {
    private int x; //coordenada x del toque
    private int y; //coordenada y
    private  int index; //indice del pointer

    /**
     * Construye el objeto del toque en pantalla
     * @param index
     * @param x Coordenadas X de la pantalla donde se hace el toque
     * @param y Coordenadas Y de la pantalla donde se hace el toque
     */
    public Touch(int index, int x, int y){
        this.index=index;
        this.x=x;
        this.y=y;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos getter y setter para propiedades del toque en pantalla
    //-----------------------------------------------------------------------------------------
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
