package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.android.app.R

class Upi : AppCompatActivity() {

    lateinit var upiNext :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upi)

        upiNext = findViewById(R.id.upi_next)

        upiNext.setOnClickListener {
            val intent = Intent(this,ContactTnformationActivity::class.java)
            startActivity(intent)
        }

    }
}