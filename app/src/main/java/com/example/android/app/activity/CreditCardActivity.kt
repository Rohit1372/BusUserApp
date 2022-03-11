package com.example.android.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.android.app.R

class CreditCardActivity : AppCompatActivity() {

    lateinit var credit_card_next : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        credit_card_next = findViewById(R.id.credit_card_next)

        credit_card_next.setOnClickListener {
            Toast.makeText(this,"Next",Toast.LENGTH_SHORT).show()
        }

    }
}