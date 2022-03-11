package com.example.android.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.android.app.R

class DebitCardActivity : AppCompatActivity() {

    lateinit var debit_card_next : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debit_card)

        debit_card_next = findViewById(R.id.debit_card_next)

        debit_card_next.setOnClickListener {
            Toast.makeText(this,"Next", Toast.LENGTH_SHORT).show()
        }

    }
}