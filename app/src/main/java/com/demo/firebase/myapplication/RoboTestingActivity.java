package com.demo.firebase.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RoboTestingActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robo_testing);
        textView = findViewById(R.id.robo_text);
        textView.setText("You have preformed Robo testing");
    }
}
