package com.example.kohliassessmentquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.transition.platform.MaterialFadeThrough;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setExitTransition(new MaterialFadeThrough().addTarget(findViewById(R.id.mAContainer)));
        getWindow().setEnterTransition(new MaterialFadeThrough().addTarget(findViewById(R.id.mAContainer)));
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("Assessment", MODE_PRIVATE);

        int saved = sharedPreferences.getInt("SAVE", 0);

        TextView tvHS = findViewById(R.id.tVHighScore);
        String highScore = "High Score = " + saved;
        tvHS.setText(highScore);

        findViewById(R.id.buttonStartQuiz).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuizActivity.class)));
    }
}