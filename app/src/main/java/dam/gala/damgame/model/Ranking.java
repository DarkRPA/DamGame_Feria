package dam.gala.damgame.model;

import java.time.LocalDateTime;

public class Ranking {
    private Player player;
    private long score;
    private int award;
    private LocalDateTime timestamp;

    /**
     * Construye el ranking de puntuaciones a partir de las propiedades indicadas como parámetros
     * @param player Jugador del ranking
     * @param score Puntuación obtenida
     * @param award Reconocimiento
     * @param timestamp Momento en que entró en el ranking
     */
    public Ranking(Player player, long score, int award, LocalDateTime timestamp) {
        this.player = player;
        this.score = score;
        this.award = award;
        this.timestamp = timestamp;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos getter y setter para las propiedades del ranking
    //-----------------------------------------------------------------------------------------
    public Player getPlayer() {
        return player;
    }

    public long getScore() {
        return score;
    }

    public int getAward() {
        return award;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
