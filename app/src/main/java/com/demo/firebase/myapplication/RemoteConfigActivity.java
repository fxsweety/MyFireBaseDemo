package com.demo.firebase.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RemoteConfigActivity extends AppCompatActivity {
    private String text;
    private boolean isWatchedNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_config);
        TextView tvSeriesText = findViewById(R.id.tv_series_text);
        ImageView imageView = findViewById(R.id.tv_series_image);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            text = extras.getString("text");
            isWatchedNow = extras.getBoolean("image");

        }
        tvSeriesText.setText(text);

        if(isWatchedNow) {
            imageView.setImageDrawable(getDrawable(R.drawable.tv_series_2));
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.tv_series_1));
        }

        Button close = findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
