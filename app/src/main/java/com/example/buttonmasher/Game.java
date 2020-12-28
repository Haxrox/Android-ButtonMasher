package com.example.buttonmasher;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    View view;
    Profile profile;

    CountDownTimer gameTimer;
    CountDownTimer cpuTimer;

    int runTime = 0;
    int userClicks = 0;
    int cpuClicks = 0;

    public Game(View view, Profile profile, int length) {
        // Initializing game
        this.view = view;
        this.profile = profile;
        // UI Objects
        TextView timeLeft = view.findViewById(R.id.timeLeft);
        TextView userBox = view.findViewById(R.id.userScoreBox);
        TextView cpuBox = view.findViewById(R.id.cpuScoreBox);
        Button startButton = view.findViewById(R.id.buttonStart);
        Button clickButton = view.findViewById(R.id.clickButton);
        // Initializing UI
        timeLeft.setText(String.valueOf(length));
        userBox.setText(String.valueOf(userClicks));
        cpuBox.setText(String.valueOf(cpuClicks));
        // GameTimer
        gameTimer = new CountDownTimer((long)length * 1000, (long)1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft.setText(millisUntilFinished / 1000 +"s");
            }
            @Override
            public void onFinish() {
                cpuTimer.cancel();
                end();
            }
        };
        // UserClicks
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClicks ++;
                userBox.setText(String.valueOf(userClicks));
            }
        });
        // CPU Timer
        long computerInterval = (long) ((Math.random()*200) + 1);
        cpuTimer = new CountDownTimer((long)length * 1000, computerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                cpuClicks++;
                cpuBox.setText(String.valueOf(cpuClicks));
            }
            @Override
            public void onFinish() {
                cpuTimer.cancel();
            }
        };
        gameTimer.start();
        cpuTimer.start();
        clickButton.setEnabled(true);
        startButton.setEnabled(false);
        MainActivity.getInstance().displayToast("The computer is clicking at " + computerInterval + "ms, good luck!");
    }

    public void end() {
        Log.i("Game", "End");
        // UI Objects
        Button startButton = view.findViewById(R.id.buttonStart);
        Button clickButton = view.findViewById(R.id.clickButton);
        // Enabling / disabling buttons
        clickButton.setEnabled(false);
        startButton.setEnabled(true);
        // Getting winner
        int clickDifference = userClicks - cpuClicks;
        if (userClicks > cpuClicks) { // User won
            profile.incrementWins();
            MainActivity.getInstance().displayToast("You out-clicked the computer by " + Math.abs(clickDifference) + " clicks!");
        } else if (cpuClicks > userClicks) { // CPU won
            MainActivity.getInstance().displayToast("The computer has out-clicked you by " + Math.abs(clickDifference) + " clicks!");
        } else { // No-one won
            MainActivity.getInstance().displayToast("You and the computer has tied!");
        }
        profile.incrementClicks(userClicks + clickDifference);
    }
}
