package dam.gala.damgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.damgame.R;

import dam.gala.damgame.utils.GameUtil;

public class ActMainStart extends AppCompatActivity {
    private final int GAME_ACTIVITY_ACTION = 2;
    private final int SETTINGS_ACTION =1;
    private int sceneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main_start);

        //hideSystemUI();
        setTema();

        Button btLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent preferences = new Intent(ActMainStart.this, GameActivity.class);
                startActivityForResult(preferences, GAME_ACTIVITY_ACTION);
            }
        });

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

    private void setTema(){
        this.sceneCode = Integer.parseInt(getSharedPreferences(this.getTitle().toString(),
                Context.MODE_PRIVATE).
                getString("theme_setting",String.valueOf(GameUtil.TEMA_SELVA)));
        switch(this.sceneCode){
/*            case GameUtil.TEMA_DESIERTO:
                setTheme(R.style.Desert_DamGame);
                break;*/
            case GameUtil.TEMA_SELVA:
                setTheme(R.style.Jungle_DamGame);
                break;
            default:
                setTheme(R.style.Jungle_DamGame);
                break;
        }

    }


}