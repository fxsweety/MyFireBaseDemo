package com.demo.firebase.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CrashlyticsActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crashlytics);
       // textView = findViewById(R.id.crash_text_id);
        textView.setText("Let's crash");

    }
}
