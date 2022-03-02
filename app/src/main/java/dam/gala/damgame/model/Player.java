package dam.gala.damgame.model;

/**
 * Jugador
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class Player {
    private String nick;
    private String password;
    private String email;

    /**
     * Construye el objeto del jugador a partir de nick, clave y correo electrónico
     * @param nick Usuario del juego
     * @param password Clave del jugador
     * @param email Correo electrónico, se usará para enviar estadísticas de juego
     */
    public Player(String nick, String password, String email) {
        this.nick = nick;
        this.password = password;
        this.email = email;
    }

    //-----------------------------------------------------------------------------------------
    //Métodos getter y setter
    //-----------------------------------------------------------------------------------------

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
