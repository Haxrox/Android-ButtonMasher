package com.example.buttonmasher.ui.Play;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.buttonmasher.MainActivity;
import com.example.buttonmasher.R;

public class PlayFragment extends Fragment {

    private PlayViewModel playViewModel;
    // Constants
    private static final int GAME_LENGTH = 15;
    private static final int MIN_CPU_SPEED = 75;
    private static final int MAX_CPU_SPEED = 200;

    // Scores
    int cpuClicks, userClicks;

    // UI Objects
    private TextView timeLeft, userBox, cpuBox;
    private Button startButton, clickButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playViewModel = new ViewModelProvider(this).get(PlayViewModel.class);
        // Initializing variables to be same as the data in the PlayViewModel
        cpuClicks = playViewModel.getCpuClicks();
        userClicks = playViewModel.getUserClicks();

        // UI Objects
        View root = inflater.inflate(R.layout.fragment_play, container, false);
        timeLeft = root.findViewById(R.id.timeLeft);
        userBox = root.findViewById(R.id.userScoreBox);
        cpuBox = root.findViewById(R.id.cpuScoreBox);
        startButton = root.findViewById(R.id.buttonStart);
        clickButton = root.findViewById(R.id.clickButton);

        // Button listeners
        // Start the game
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        // Increment user score by 1
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClicks++;
                userBox.setText(String.valueOf(userClicks));
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void newGame() {
        // Initializing UI
        timeLeft.setText(String.valueOf(GAME_LENGTH));
        userBox.setText(String.valueOf(playViewModel.getUserClicks()));
        cpuBox.setText(String.valueOf(playViewModel.getCpuClicks()));

        // Variables
        userClicks = 0;
        cpuClicks = 0;
        playViewModel.setUserClicks(userClicks);
        playViewModel.setCpuClicks(cpuClicks);

        // CPU
        long computerInterval = (long) ((Math.random()*(MAX_CPU_SPEED - MIN_CPU_SPEED) + MIN_CPU_SPEED));
        CountDownTimer cpuTimer = new CountDownTimer((long)GAME_LENGTH * 1000, computerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                cpuClicks++;
                cpuBox.setText(String.valueOf(cpuClicks));
            }
            @Override
            public void onFinish() {
                CountDownTimer cpuTimer = playViewModel.getCpuTimer();
                cpuTimer.cancel();
            }
        };
        playViewModel.setCpuTimer(cpuTimer);

        // GameTimer
        CountDownTimer gameTimer = new CountDownTimer((long)GAME_LENGTH * 1000, (long)1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft.setText(String.format("%ds", millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                CountDownTimer cpuTimer = playViewModel.getCpuTimer();
                cpuTimer.cancel();
                endGame();
            }
        };
        playViewModel.setGameTimer(gameTimer);

        // Beginning
        gameTimer.start();
        cpuTimer.start();
        clickButton.setEnabled(true);
        startButton.setEnabled(false);
        MainActivity.getInstance().displayToast("The computer is clicking at " + computerInterval + "ms, good luck!");
    }

    public void endGame() {
        // Setting to begin state
        clickButton.setEnabled(false);
        startButton.setEnabled(true);
        // Getting winner
        int clickDifference = userClicks - cpuClicks;
        if (userClicks > cpuClicks) { // User won
            // profile.incrementWins();
            MainActivity.getInstance().displayToast("You out-clicked the computer by " + Math.abs(clickDifference) + " clicks!");
        } else if (cpuClicks > userClicks) { // CPU won
            MainActivity.getInstance().displayToast("The computer has out-clicked you by " + Math.abs(clickDifference) + " clicks!");
        } else { // No-one won
            MainActivity.getInstance().displayToast("You and the computer has tied!");
        }
        // profile.incrementClicks(userClicks + clickDifference);
    }
}