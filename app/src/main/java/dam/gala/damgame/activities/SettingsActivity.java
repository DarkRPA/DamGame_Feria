package dam.gala.damgame.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.damgame.R;
import dam.gala.damgame.fragments.SettingsFragment;
import dam.gala.damgame.utils.GameUtil;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Actividad para las preferencias de la aplicación
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * Método de callback cuya llamada se produce antes de que la actividad llegue al estado
     * 'Activa'
     * @param savedInstanceState Contenedor para almacenar información de la actividad o para
     *                           pasarla como parámetro
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTema();
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment(this)).commit();
    }

    /**
     * Evento de botón atrás, finaliza esta actividad
     */
    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK,getIntent());
        super.onBackPressed();
    }
    /**
     * Método callback que genera la actividad para cargar el menú especificado
     * @param menu Menú especificado (Menu)
     * @return Devuelve true si se crea el menú, en caso contrario false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.settings_menu,menu);
        return true;
    }
    /**
     * Evento para la opción de menú de volver atrás
     * @param item Item de menú sobre el que se produce el evento
     * @return Devuelve true si se ha gestionado el evento, en caso contrario false
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.imBack:
                setResult(Activity.RESULT_OK,getIntent());
                finish();
                break;
        }
        return true;
    }

    /**
     * Obtiene el tema de las preferencias del juego y lo asigna como tema de la actividad
     */
    private void setTema(){
        int sceneCode = Integer.valueOf(getDefaultSharedPreferences(this).getString("theme_setting","100"));
        switch(sceneCode){
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
}