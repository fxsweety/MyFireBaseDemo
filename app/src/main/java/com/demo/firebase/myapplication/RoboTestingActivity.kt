package com.demo.firebase.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class RoboTestingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.robo_testing)
        val closeButton = findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener { finish() }
    }
}
