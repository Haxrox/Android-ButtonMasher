package com.example.buttonmasher.ui.Play;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayViewModel extends ViewModel {

    private MutableLiveData<Integer> cpuClicks;
    private MutableLiveData<Integer> userClicks;
    private MutableLiveData<CountDownTimer> gameTimer;
    private MutableLiveData<CountDownTimer> cpuTimer;

    public PlayViewModel() {
        cpuClicks = new MutableLiveData<>();
        cpuClicks.setValue(0);
        userClicks = new MutableLiveData<>();
        userClicks.setValue(0);
        gameTimer = new MutableLiveData<>();
        cpuTimer = new MutableLiveData<>();
    }

    public int getCpuClicks() {
        return cpuClicks.getValue();
    }

    public void setCpuClicks(int cpuClicks) {
        this.cpuClicks.setValue(cpuClicks);
    }

    public int getUserClicks() {
        return userClicks.getValue();
    }

    public void setUserClicks(int userClicks) {
        this.userClicks.setValue(userClicks);
    }

    public CountDownTimer getGameTimer() {
        return gameTimer.getValue();
    }

    public void setGameTimer(CountDownTimer gameTimer) {
        this.gameTimer.setValue(gameTimer);
    }

    public CountDownTimer getCpuTimer() {
        return cpuTimer.getValue();
    }

    public void setCpuTimer(CountDownTimer cpuTimer) {
        this.cpuTimer.setValue(cpuTimer);
    }
}