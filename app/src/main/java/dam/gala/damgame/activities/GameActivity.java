package dam.gala.damgame.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.damgame.R;

import java.util.ArrayList;
import java.util.List;

import dam.gala.damgame.controllers.AudioController;
import dam.gala.damgame.data.DatabaseManager;
import dam.gala.damgame.fragments.QuestionDialogFragment;
import dam.gala.damgame.interfaces.InterfaceDialog;
import dam.gala.damgame.model.GameConfig;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.scenes.Scene;
import dam.gala.damgame.utils.GameUtil;
import dam.gala.damgame.utils.RandomGenerator;
import dam.gala.damgame.views.GameView;

/**
 * Actividad principal
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity implements InterfaceDialog {
    private final int SETTINGS_ACTION =1;
    private Play gameMove;
    private int sceneCode;
    private GameView gameView;
    private GameConfig config;
    private Scene scene;
    private AudioController audioController;
    //Vista para la puntuación;
    private TextView tvPoints;
    private ImageView ivPoints;
    //Array para las vidas;
    private ArrayList<ImageView> lifes;
    //Vista para las respuestas;
    private TextView tvAnswers;
    private ImageView ivAnswers;
    private DatabaseManager databaseManager;

    /**
     * Método de callback del ciclo de vida de la actividad, llamada anterior a que la actividad
     * pasé al estado 'Activa'
     * @param savedInstanceState Contenedor para paso de parámetros y guardar información entre
     *                           distintos estados de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTema();
        setContentView(R.layout.activity_main);

        Button btnComenzar = findViewById(R.id.btIniciar);
        Button btnSettings = findViewById(R.id.btnSettings);

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferences = new Intent(GameActivity.this, SettingsActivity.class);
                startActivityForResult(preferences, SETTINGS_ACTION);
            }
        });

        // This callback will only be called when MyFragment is at least Started.
       /* OnBackPressedCallback callback = new OnBackPressedCallback(true *//* enabled by default *//*) {
            @Override
            public void handleOnBackPressed() {
                GameActivity.this.gameView.endGame(true);
                finish();
                System.exit(0);
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);*/

        this.databaseManager = DatabaseManager.getInstance(getApplicationContext());
        databaseManager.open();

        databaseManager.loadQuestions();

        this.sceneCode = Integer.parseInt(getSharedPreferences(this.getTitle().toString(),
                Context.MODE_PRIVATE).
                getString("theme_setting",String.valueOf(GameUtil.TEMA_DESIERTO)));

        this.scene = Play.getSceneByCode(this, this.sceneCode);
        this.audioController = new AudioController(this);
        this.audioController.startMenuMusic();
        //Ya tenemos la scene ahora reproducimos la canción de inicio



        // ArrayList<Question> preguntas = databaseManager.getPreguntas();
    }

    public Scene getScene(){
        return this.scene;
    }

    @Override
    protected void onResume() {
        //Tenemos que actualizar la escena
        this.sceneCode = Integer.parseInt(getSharedPreferences(this.getTitle().toString(),
                Context.MODE_PRIVATE).
                getString("theme_setting",String.valueOf(GameUtil.TEMA_DESIERTO)));

        this.scene = Play.getSceneByCode(this, this.sceneCode);
        if(this.audioController != null){
            this.audioController.stopAudioEndGame();
            this.audioController.startMenuMusic();
        }
        super.onResume();
    }

    /**
     * Inicio del juego
     */
    private void startGame(){

        this.gameMove = Play.createGameMove(this,this.sceneCode);
        this.config = new GameConfig(this.scene);
        this.getPlay().setConfig(this.config);

        setContentView(R.layout.activity_game);

        this.gameView = findViewById(R.id.svGame);


        List<Question> questions = databaseManager.getQuestions();

        gameView.setQuestions(questions);
        databaseManager.close();

        //hideSystemUI();

        //this.audioController = this.gameView.getAudioController();
        this.gameView.setAudioController(this.audioController);
        this.audioController.startSceneAudioPlay();

        this.loadScoreComponents();
    }

    /**
     * Carga las imágenes del marcador de vidas, puntos y respuestas
     */
    private void loadScoreComponents(){
        this.lifes = new ArrayList<>();
        this.lifes.add(findViewById(R.id.ivBouncy1));
        this.lifes.add(findViewById(R.id.ivBouncy2));
        this.lifes.add(findViewById(R.id.ivBouncy3));

        this.ivAnswers = findViewById(R.id.ivAnswers);
        this.ivAnswers.setImageBitmap(this.scene.getScoreAnswers());

        this.ivPoints = findViewById(R.id.ivPoints);
        this.ivPoints.setImageBitmap(this.scene.getScorePoints());
    }
    /**
     * Muestra el cuadro de diálogo de la pregunta
     */
    private void showQuestionDialog(){
        //código para probar el cuadro de diálogo
        Button btPregunta = findViewById(R.id.btIniciar);

        MediaPlayer mediaPlayerJuego = MediaPlayer.create(this, R.raw.my_street);
        mediaPlayerJuego.setLooping(true);
        mediaPlayerJuego.start();

        btPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showQuestionDialog();
                startGame();
            }
        });
        //aquí habrá que obtener aleatoriamente una pregunta
        //del repositorio
        CharSequence[] respuestas = new CharSequence[3];
        String enunciado = "Selecciona cuál de los siguientes planetas no está en la vía láctea";
        respuestas[0]="Marte";
        respuestas[1]="Nébula";
        respuestas[2]="Mercurio";
        int[] respuestasCorrectas = new int[]{1};
        Question question =new Question("¿Cuánto es 2+2?", GameUtil.PREGUNTA_COMPLEJIDAD_ALTA, null, null, Question.TEN_POINTS);

        QuestionDialogFragment qdf = null;
        qdf.setCancelable(false);
        qdf.show(getSupportFragmentManager(),null);
    }
    /**
     * Establece el tema seleccionado en las preferencias
     */
    private void setTema(){
        this.sceneCode = Integer.parseInt(getSharedPreferences(this.getTitle().toString(),
                Context.MODE_PRIVATE).
                getString("theme_setting",String.valueOf(GameUtil.TEMA_SELVA)));
        switch(this.sceneCode){
            case GameUtil.TEMA_DESIERTO:
                setTheme(R.style.Desert_DamGame);
                break;
            case GameUtil.TEMA_SELVA:
                setTheme(R.style.Jungle_DamGame);
                break;
            default:
                setTheme(R.style.Jungle_DamGame);
                break;
        }

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //hideSystemUI();
        }
    }
    /**
     * Elimina la barra de acción y deja el mayor área posible de pantalla libre
     */
    public void hideSystemUI(){
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    /**
     * Obitiene la jugada actual
     * @return Devuelve la jugada actual (Play)
     */
    public Play getPlay(){
        return this.gameMove;
    }
    /**
     * Obtiene la configuración del juego
     * @return Devuelve la configuración del juego (GameConfig)
     */
    public GameConfig getGameConfig(){
        return this.config;
    }
    /**
     * Obtiene el controlador audio
     * @return Devuelve el controlador de audio del juego (AudioController)
     */
    public AudioController getAudioController(){
        return this.audioController;
    }
    @Override
    public void setRespuesta(String respuesta) {
        this.gameView.setStopGame(false);
        this.gameView.restart();
    }
    /**
     * Menú principal de la aplicación
     * @param menu Menú de aplicación
     * @return Devuelve true si se ha creado el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO Esto se debe mejorar y sistituir en la interfaz por controles vistosos
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    /**
     * Evento de selección de elemento de menú
     * @param item Item de menú
     * @return Devuelve true si se ha tratado el evento recibido
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.imSettings:
                Intent preferences = new Intent(GameActivity.this, SettingsActivity.class);
                startActivityForResult(preferences, SETTINGS_ACTION);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK,getIntent());
        super.onBackPressed();
    }

    /**
     * Método de callback para recibir el resultado de una intención llamada para devolver un
     * resultado
     * @param requestCode Código de la petición (int)
     * @param resultCode Código de respuesta (int)
     * @param data Intención que devuelve el resultado, la que produce el callback
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== SETTINGS_ACTION){
            if(resultCode== Activity.RESULT_OK){
                //TODO Este método hay que revisarlo y borrarlo si finalmente no se usa
            }
        }
    }

    /**
     * Actualiza las imágenes de las vidas disponibles, oculta la última imagen de las vidas
     * @param index Índice la imagen a ocultar
     */
    public void updateLifes(Integer index){
        if (index > 3){
            if (getPlay().getLifes() > 3){
                TextView textView = findViewById(R.id.tvExtraLifes);

                textView.setVisibility(View.VISIBLE);

                textView.setText("+ " + (getPlay().getLifes() - 3));
            }

            return;
        }

        if(index>=0){
            if (getPlay().isHasWonLife()){
                this.lifes.get(index-1).setVisibility(View.VISIBLE);
            }else{
                if (index > 2){
                    TextView textView = findViewById(R.id.tvExtraLifes);

                    textView.setVisibility(View.INVISIBLE);
                    return;
                }
                this.lifes.get(index).setVisibility(View.INVISIBLE);
            }
        }

    }

    public void updateScore(Integer val){
        tvPoints = findViewById(R.id.tvPoints);

        this.tvPoints.setText(String.valueOf(val));
    }

    public void updateScoreAnswer(Integer val){
        tvAnswers = findViewById(R.id.tvAnswers);

        this.tvAnswers.setText(String.valueOf(val));
    }

    public GameView getGameView(){
        return this.gameView;
    }
}