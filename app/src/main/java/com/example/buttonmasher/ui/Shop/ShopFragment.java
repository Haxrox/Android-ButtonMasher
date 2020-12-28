package com.example.buttonmasher.ui.Shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.buttonmasher.MainActivity;
import com.example.buttonmasher.R;
import com.example.buttonmasher.ui.Settings.SettingsFragment;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("ShopFragment", "OnCreateView");
        shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        TextView clickBox = root.findViewById(R.id.totalClicks);
        clickBox.setText(MainActivity.getProfile().getClicks());

        return root;
    }
}