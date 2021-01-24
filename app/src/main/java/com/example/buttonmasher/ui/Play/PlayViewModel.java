package com.example.buttonmasher.ui.Play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buttonmasher.Game;

public class PlayViewModel extends ViewModel {

    private MutableLiveData<Game> game;

    public PlayViewModel() {

    }
}