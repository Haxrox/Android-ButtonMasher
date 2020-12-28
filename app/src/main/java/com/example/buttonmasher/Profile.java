package com.example.buttonmasher;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Profile {
    private static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private String name = "";
    private int clicks = 0;
    private int wins = 0;
    List<Perk> inventory;

    public Profile(String name, int clicks) {

    }

    public int getClicks() {
        return clicks;
    }

    public void incrementClicks(int value) {
        clicks += value;
    }

    public void incrementWins() {
        wins ++;
    }
}
