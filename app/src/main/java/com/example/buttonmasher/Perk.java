package com.example.buttonmasher;

import android.widget.Button;
import android.widget.TextView;

public class Perk {
    private String name = "";
    private int cost;
    private boolean purchased;
    TextView nameLabel;
    TextView costLabel;
    Button purchaseButton;

    public Perk(String name, int cost, boolean purchased) {
        this.name = name;
        this.cost = cost;
        this.purchased = purchased;
    }

    public int getCost() {
        return cost;
    }

    public boolean isPurchased() {
        return purchased;
    }
}
