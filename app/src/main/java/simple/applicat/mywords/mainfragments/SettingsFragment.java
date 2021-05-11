package simple.applicat.mywords.mainfragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import simple.applicat.mywords.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}