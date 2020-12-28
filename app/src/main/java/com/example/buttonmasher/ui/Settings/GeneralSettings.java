package com.example.buttonmasher.ui.Settings;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.buttonmasher.MainActivity;
import com.example.buttonmasher.R;

public class GeneralSettings extends PreferenceFragmentCompat {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("GeneralSettings", "OnCreate");
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.i("GeneralSettings", "onCreatePreferences");
        setPreferencesFromResource(R.xml.general_preferences, rootKey);
        Preference preference = findPreference("totalClicks");

        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences sharedPreferences = MainActivity.getInstance().getSharedPreferences("someKey", Context.MODE_PRIVATE);
                sharedPreferences.edit()
                    .putString("totalClicks", String.valueOf(newValue)).apply();
                return true;
            }
        });
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("preference")) {
            Log.i("GeneralSettings", "Preference clicked!");
        }
        return super.onPreferenceTreeClick(preference);
    }
}
