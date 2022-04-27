package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.android.app.R

class YouCanPay : AppCompatActivity() {

    lateinit var totalSeats2 : TextView
    lateinit var payable2 : TextView

    lateinit var upi : TextView
    lateinit var net_banking : TextView
    lateinit var credit_card : TextView
    lateinit var debit_card : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_can_pay)

        totalSeats2 = findViewById(R.id.totalSeats2)
        payable2 = findViewById(R.id.payable2)

        upi = findViewById(R.id.upi)
        net_banking = findViewById(R.id.net_bamking)
        credit_card = findViewById(R.id.credit_card)
        debit_card = findViewById(R.id.debit_card)

        val selectedSeatsIntent = intent.getStringExtra("selectedSeats").toString()
        val priceIntent = intent.getStringExtra("price")

        totalSeats2.text = selectedSeatsIntent

        val sI = Integer.parseInt(selectedSeatsIntent)
        val pI = Integer.parseInt(priceIntent)
        val p1 = sI*pI
        payable2.text = p1.toString()

        upi.setOnClickListener {
            val intent = Intent(this,Upi::class.java)
            startActivity(intent)
        }

        net_banking.setOnClickListener {
            val intent = Intent(this,Net_Banking::class.java)
            startActivity(intent)
        }

        credit_card.setOnClickListener {
            val intent = Intent(this,CreditCardActivity::class.java)
            startActivity(intent)
        }

        debit_card.setOnClickListener {
            val intent = Intent(this,DebitCardActivity::class.java)
            startActivity(intent)
        }

    }
}