package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.android.app.R

class Net_Banking : AppCompatActivity() {

    lateinit var netBankingNext : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_banking)

        netBankingNext = findViewById(R.id.net_banking_next)

        netBankingNext.setOnClickListener {
            Toast.makeText(this,"Next",Toast.LENGTH_SHORT).show()
        }

    }
}