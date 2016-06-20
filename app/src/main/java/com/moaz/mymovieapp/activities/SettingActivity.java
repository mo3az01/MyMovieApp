package com.moaz.mymovieapp.activities;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.moaz.mymovieapp.R;

/**
 * Created by XKCL0301 on 6/20/2016.
 */
public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));

    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = ((ListPreference) preference);
            int prefIdx = listPreference.findIndexOfValue(value.toString());
            if (prefIdx >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIdx]);
            }
        } else {
            preference.setSummary(value.toString());
        }
        return true;
    }
}
