package dam.gala.damgame.model;

import java.util.List;

/**
 * Representa la pregunta
 * @author Iordache Mihai Laurentiu
 * @version 1.0
 */
public class Question {
    private String question;
    private int difficulty;
    private List<String> answers;
    private int type;
    private List<Integer> indiceRespuestasCorrectas;

    // TODO RADIO BUTTONS
    public static final int SIMPLE = 10;

    // TODO CHECKBOX
    public static final int MULTIPLE = 20;

    // TODO SPINNER
    public static final int LIST = 30;

    private final int POINTS;
    public static final int TWO_POINTS = 2;
    public static final int FOUR_POINTS = 4;
    public static final int SIX_POINTS = 6;
    public static final int EIGHT_POINTS = 8;
    public static final int TEN_POINTS = 10;

    public Question(String question, int difficulty, List<String> answers, List<Integer> indiceRespuestasCorrectas, int puntos) {
        this.question = question;
        this.difficulty = difficulty;
        this.answers = answers;
        this.indiceRespuestasCorrectas = indiceRespuestasCorrectas;
        this.POINTS = puntos;
    }

    public Question(String question, int difficulty, int type, List<String> answers, List<Integer> indiceRespuestasCorrectas, int puntos) {
        this.question = question;
        this.difficulty = difficulty;
        this.answers = answers;
        this.indiceRespuestasCorrectas = indiceRespuestasCorrectas;
        this.POINTS = puntos;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<Integer> getIndiceRespuestasCorrectas() {
        return indiceRespuestasCorrectas;
    }

    public void setIndiceRespuestasCorrectas(List<Integer> indiceRespuestasCorrectas) {
        this.indiceRespuestasCorrectas = indiceRespuestasCorrectas;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPOINTS() {
        return POINTS;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", difficulty=" + difficulty +
                ", answers=" + answers +
                ", type=" + type +
                ", indiceRespuestasCorrectas=" + indiceRespuestasCorrectas +
                ", POINTS=" + POINTS +
                '}';
    }
}
