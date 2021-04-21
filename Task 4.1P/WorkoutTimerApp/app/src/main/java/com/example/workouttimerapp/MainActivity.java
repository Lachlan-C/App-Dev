package com.example.workouttimerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    private Chronometer chronometer;
    private long pausedTime, prevWorkoutTime;
    private ImageButton start,pause,record;
    private boolean running;
    private EditText WorkoutName;
    private TextView prevWorkout;
    private String PrevWorkoutName;
    private SharedPreferences sharedPreferences;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TIMER = "timer";
    public static final String WORKOUT = "workout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.Start);
        pause = findViewById(R.id.Pause);
        record = findViewById(R.id.Record);
        prevWorkout = findViewById(R.id.PrevWorkout);
        WorkoutName = findViewById(R.id.EnterWorkout);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());

        //loading preferences
        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        PrevWorkoutName = sharedPreferences.getString(WORKOUT, "");
        prevWorkoutTime = sharedPreferences.getLong(TIMER, 0);


        int secs = (int) (prevWorkoutTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        prevWorkout.setText("You spent " + String.format("%d:%02d", mins, secs) + " on " + PrevWorkoutName + " last time");


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running == false)
                {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pausedTime);
                    chronometer.start();
                    running = true;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running == true)
                {
                    chronometer.stop();
                    pausedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                if (running) {
                    prevWorkoutTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                }
                else {
                    prevWorkoutTime = pausedTime;
                }
                //FIX UP STOPPING CODE // CODE RUNS AND STARTS COUNTING AFTER RESET // CODE DOESN'T GET RIGHT TIME WHEN PAUSED
                pausedTime = 0;
                running = false;
                chronometer.setBase(SystemClock.elapsedRealtime());

                //save PrevWorkOutName and Prev Workout Time
                PrevWorkoutName = WorkoutName.getText().toString();
                int secs = (int) (prevWorkoutTime/1000);
                int mins = secs / 60;
                secs = secs % 60;
                prevWorkout.setText("You spent " + String.format("%d:%02d", mins, secs) + " on " + PrevWorkoutName + " last time");

                sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(WORKOUT,PrevWorkoutName);
                editor.putLong(TIMER, prevWorkoutTime);
                editor.apply();

            }
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("running", running);
        outState.putLong("PausedTime", pausedTime);
        outState.putLong("Base", chronometer.getBase());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        running = savedInstanceState.getBoolean("running");
        pausedTime = savedInstanceState.getLong("PausedTime");
        chronometer.setBase(savedInstanceState.getLong("Base"));

        if (running)
        {
            chronometer.start();

        } else {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime() - pausedTime);
        }
    }
}