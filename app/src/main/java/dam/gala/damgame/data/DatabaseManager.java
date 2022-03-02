package dam.gala.damgame.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dam.gala.damgame.model.Question;
import dam.gala.damgame.utils.GameUtil;

public class DatabaseManager {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseManager instance;
    private Cursor c = null;
    private List<Question> questions;

    public DatabaseManager(Context context){
        this.openHelper = new DataBaseHelper(context);
    }

    public static DatabaseManager getInstance(Context context){
        if (instance == null){
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if (db != null){
            this.db.close();
        }
    }

    public void loadQuestions(){
        c = db.rawQuery("SELECT enunciado, group_concat(respuesta,'{') AS respuestas, compleja, simple, group_concat(correcta) FROM 'PREGUNTA_RESPUESTA' group by enunciado", new String[]{});

        boolean isQuestionComplex = false;

        List<Question> questions = new ArrayList<>();
        int pos = 0;

        while (c.moveToNext()){
            isQuestionComplex = false;
            pos = 0;

            List<Integer> correctIndices = new ArrayList<>();
            List<String> questionAnswers = new ArrayList<>();

            String questionText = c.getString(0);

            StringTokenizer stringTokenizerAnswer = new StringTokenizer(c.getString(1), "{");

            while (stringTokenizerAnswer.hasMoreTokens()){
                questionAnswers.add(stringTokenizerAnswer.nextToken());
            }

            int complex = c.getInt(2);
            int simple = c.getInt(3);

            if (complex == 1 && simple == 0) isQuestionComplex = true;
            else if (simple == 1 && complex == 0) isQuestionComplex = false;
            else isQuestionComplex = false;


            StringTokenizer stringTokenizerCorrectIndices = new StringTokenizer(c.getString(4),",");

            while (stringTokenizerCorrectIndices.hasMoreTokens()){
                int index = Integer.parseInt(stringTokenizerCorrectIndices.nextToken());

                if (index == 1) correctIndices.add(pos);
                pos++;
            }

            Question question = new Question(questionText, isQuestionComplex ? GameUtil.PREGUNTA_COMPLEJIDAD_ALTA : GameUtil.PREGUNTA_COMPLEJIDAD_BAJA,
                    questionAnswers, correctIndices, isQuestionComplex ? Question.TEN_POINTS : Question.FOUR_POINTS);

            question.setType(question.getIndiceRespuestasCorrectas().size() > 1 ? Question.MULTIPLE : Question.SIMPLE);

            questions.add(question);
        }

        this.questions = questions;
    }

    public List<Question> getQuestions(){
        return this.questions;
    }

    public ArrayList<Question> getPreguntas(){
        ArrayList<Question> preguntas=new ArrayList<>();



        return preguntas;
    }
}
