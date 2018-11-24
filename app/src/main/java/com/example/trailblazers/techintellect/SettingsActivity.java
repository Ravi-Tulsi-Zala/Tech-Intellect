package com.example.trailblazers.techintellect;

/*************************************************************************************************************************************************************
 Program: Settings activity which would handle the settings screen
 Author: Aravind
 Date of creation: 11-Nov-2018
 *************************************************************************************************************************************************************/
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

public class SettingsActivity extends AppCompatPreferenceActivity {

    public static final String KEY_PREF_SWITCH = "vibrate_switch";
    public static boolean isVibrateSwitchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();

    }
    //Fragment class for preferences
    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_screen); //loading the preference xml

            // vibrate switch change listener
            bindPreferenceSummaryToValue(findPreference(getString(R.string.haptic_key)));

        }

        private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getBoolean(preference.getKey(), false));
        }

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                if (preference instanceof SwitchPreference) {

                    boolean switched = Boolean.parseBoolean(stringValue);
                    isVibrateSwitchOn = switched;
                }
                return true;
            }
        };

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("haptic")) {
                boolean test = sharedPreferences.getBoolean("haptic", false);
                isVibrateSwitchOn = test;
            }
        }

        @Override
        public void onResume() {
            super.onResume();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean test = preferences.getBoolean("haptic", false);
            isVibrateSwitchOn = test;
        }
    }
}

