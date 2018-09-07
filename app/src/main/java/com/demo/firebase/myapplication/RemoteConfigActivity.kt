package com.demo.firebase.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class RemoteConfigActivity : AppCompatActivity() {
    private var text: String? = null
    private var isWatchedNow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.remote_config)
        val tvSeriesText = findViewById<TextView>(R.id.tv_series_text)
        val imageView = findViewById<ImageView>(R.id.tv_series_image)
        val extras = intent.extras
        if (extras != null) {
            text = extras.getString("text")
            isWatchedNow = extras.getBoolean("image")

        }
        tvSeriesText.text = text

        if (isWatchedNow) {
            imageView.setImageDrawable(getDrawable(R.drawable.tv_series_2))
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.tv_series_1))
        }

        val close = findViewById<Button>(R.id.close_button)
        close.setOnClickListener { finish() }
    }
}
