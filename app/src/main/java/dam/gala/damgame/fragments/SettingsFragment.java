package dam.gala.damgame.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.example.damgame.R;

/**
 * Cuadro de diálogo para las preferencias de la aplicación
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private Activity actividad;

    public SettingsFragment(Activity actividad){
        this.actividad = actividad;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        this.setPreferencesFromResource(R.xml.settings, rootKey);
        ListPreference preferencias = this.findPreference("ambient_setting");
        preferencias.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Activity actividad = SettingsFragment.this.actividad;
                SharedPreferences preferences = actividad.getSharedPreferences(actividad.getTitle().toString(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("theme_setting", (String)newValue).commit();

                return true;
            }
        });
    }


}