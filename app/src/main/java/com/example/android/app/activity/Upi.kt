package com.example.android.app.activity

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
            Toast.makeText(this,"Next",Toast.LENGTH_SHORT).show()
        }

    }
}