package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.example.android.app.R

class PaymentSuccessActivity : AppCompatActivity() {

    lateinit var lottieCheckedDone : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        supportActionBar?.hide()

        lottieCheckedDone = findViewById(R.id.lottie_checked_done)
        lottieCheckedDone.speed = 1F
        lottieCheckedDone.playAnimation()

        val timeOut = 7000
            val intent = Intent(this,HomeActivity::class.java)
            Handler().postDelayed({
                startActivity(intent)
                finish()
            },timeOut.toLong())

    }

}