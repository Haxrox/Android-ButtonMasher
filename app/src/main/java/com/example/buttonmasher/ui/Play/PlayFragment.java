package com.example.buttonmasher.ui.Play;

import android.graphics.ImageDecoder;
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
import com.example.buttonmasher.Profile;
import com.example.buttonmasher.R;

public class PlayFragment extends Fragment {
    // Constants
    private static final int GAME_LENGTH = 15;
    private static final int MIN_CPU_SPEED = 75;
    private static final int MAX_CPU_SPEED = 200;

    // ViewModel
    private PlayViewModel mPlayViewModel;

    // Scores
    int mCpuClicks, mUserClicks;

    // UI Objects
    private TextView mTimeLeft, mUserBox, mCpuBox;
    private Button mStartButton, mClickButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPlayViewModel = new ViewModelProvider(this).get(PlayViewModel.class);
        // Initializing variables to be same as the data in the PlayViewModel
        mCpuClicks = mPlayViewModel.getCpuClicks();
        mUserClicks = mPlayViewModel.getUserClicks();

        // UI Objects
        View root = inflater.inflate(R.layout.fragment_play, container, false);
        mTimeLeft = root.findViewById(R.id.timeLeft);
        mUserBox = root.findViewById(R.id.userScoreBox);
        mCpuBox = root.findViewById(R.id.cpuScoreBox);
        mStartButton = root.findViewById(R.id.buttonStart);
        mClickButton = root.findViewById(R.id.clickButton);

        // Button listeners
        // Start the game
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        // Increment user score by 1
        mClickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserClicks++;
                mUserBox.setText(String.valueOf(mUserClicks));
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
        mTimeLeft.setText(String.valueOf(GAME_LENGTH));
        mUserBox.setText(String.valueOf(mPlayViewModel.getUserClicks()));
        mCpuBox.setText(String.valueOf(mPlayViewModel.getCpuClicks()));

        // Variables
        mUserClicks = 0;
        mCpuClicks = 0;
        mPlayViewModel.setUserClicks(mUserClicks);
        mPlayViewModel.setCpuClicks(mCpuClicks);

        // CPU
        long computerInterval = (long) ((Math.random()*(MAX_CPU_SPEED - MIN_CPU_SPEED) + MIN_CPU_SPEED));
        CountDownTimer cpuTimer = new CountDownTimer((long)GAME_LENGTH * 1000, computerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCpuClicks++;
                mCpuBox.setText(String.valueOf(mCpuClicks));
            }
            @Override
            public void onFinish() {
                CountDownTimer cpuTimer = mPlayViewModel.getCpuTimer();
                cpuTimer.cancel();
            }
        };
        mPlayViewModel.setCpuTimer(cpuTimer);

        // GameTimer
        CountDownTimer gameTimer = new CountDownTimer((long)GAME_LENGTH * 1000, (long)1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeft.setText(String.format("%ds", millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                CountDownTimer cpuTimer = mPlayViewModel.getCpuTimer();
                cpuTimer.cancel();
                endGame();
            }
        };
        mPlayViewModel.setGameTimer(gameTimer);

        // Beginning
        gameTimer.start();
        cpuTimer.start();
        mClickButton.setEnabled(true);
        mStartButton.setEnabled(false);
        MainActivity.getInstance().displayToast("The computer is clicking at " + computerInterval + "ms, good luck!");
    }

    public void endGame() {
        // Setting to begin state
        mClickButton.setEnabled(false);
        mStartButton.setEnabled(true);
        MainActivity mainActivity = MainActivity.getInstance();
        Profile profile = MainActivity.getProfile();

        // Getting winner
        int clickDifference = mUserClicks - mCpuClicks;
        if (mUserClicks > mCpuClicks) { // User won
            profile.incrementWins();
            mainActivity.displayToast("You out-clicked the computer by " + Math.abs(clickDifference) + " clicks!");
        } else if (mCpuClicks > mUserClicks) { // CPU won
            profile.incrementLosses();
            mainActivity.displayToast("The computer has out-clicked you by " + Math.abs(clickDifference) + " clicks!");
        } else { // No-one won
            profile.incrementTies();
            mainActivity.displayToast("You and the computer has tied!");
        }
        // Update profile
        profile.incrementClicks(mUserClicks + clickDifference);
    }
}