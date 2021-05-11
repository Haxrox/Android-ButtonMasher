package com.example.buttonmasher;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

// Profile
public class Profile {
    private int clicks = 0;
    private int wins = 0;
    private int losses = 0;
    private int ties = 0;

    public Profile () {

    }

    public Profile(int clicks, int wins, int losses, int ties) {
        this.clicks = clicks;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public int getClicks() {
        return clicks;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public void incrementClicks(int value) {
        clicks += value;
    }

    public void incrementWins() {
        wins ++;
    }

    public void incrementLosses() {
        losses ++;
    }

    public void incrementTies() {
        ties ++;
    }
}
