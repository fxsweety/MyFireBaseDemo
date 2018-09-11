package com.demo.firebase.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class CrashlyticsActivity : AppCompatActivity() {

    internal var bookTable: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crashlytics)
        bookTable = findViewById(R.id.booke_table);
        bookTable!!.setOnClickListener {
            // Book a Table
            val intent = Intent(this@CrashlyticsActivity, BookTableActivity::class.java)
            startActivity(intent)
        }

    }
}
