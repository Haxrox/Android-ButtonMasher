package com.example.buttonmasher.ui.Settings;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.buttonmasher.MainActivity;
import com.example.buttonmasher.R;
import com.example.buttonmasher.ui.Play.PlayFragment;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel settingsViewModel;

    /*
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("SettingsFragment", "OnCreateView");
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        return root;
    }
    */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SettingsFragment", "OnCreate");


    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.i("SettingsFragment", "onCreatePreferences");
        setPreferencesFromResource(R.xml.list_settings, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        switch(preference.getKey()) {
            case "preference":
                Log.i("SettingsFragment", "Preference clicked!");
                // setPreferencesFromResource(R.xml.general_preferences, "general_settings");
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.general_settings);
                return true;
            default:
                return super.onPreferenceTreeClick(preference);
        }
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("SettingsFragment", "Options Item Selected");
        switch (item.getItemId()) {
            case android.R.id.home: {
                FragmentManager fm = getParentFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                    return true;
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

     */
}