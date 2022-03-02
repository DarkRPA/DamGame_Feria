package dam.gala.damgame.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import com.example.damgame.R;

/**
 * Cuadro de diálogo para las preferencias de la aplicación
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}