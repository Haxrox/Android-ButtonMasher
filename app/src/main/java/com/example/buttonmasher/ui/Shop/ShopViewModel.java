package com.example.buttonmasher.ui.Shop;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.buttonmasher.MainActivity;
import com.example.buttonmasher.Perk;

public class ShopViewModel extends ViewModel {


    SharedPreferences sharedPreferences;

    private MutableLiveData<Perk> perks;

    public ShopViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

        SharedPreferences sharedPreferences = MainActivity.getInstance().getSharedPreferences("someKey", Context.MODE_PRIVATE);
        mClicks = new MutableLiveData<>();
        mClicks.setValue(sharedPreferences.getString("Clicks", "0"));
    }

    public LiveData<Perk> getPerks() {
        return perks;
    }
}