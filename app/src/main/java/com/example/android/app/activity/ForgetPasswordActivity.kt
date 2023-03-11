package com.example.android.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import com.example.android.app.utils.NetworkHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var resetPasswordBtn: Button
    private lateinit var back_arrow : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        //Remove Action Bar
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.email_edt_text)
        resetPasswordBtn = findViewById(R.id.reset_pass_btn)
        back_arrow = findViewById(R.id.back_arrow)

        back_arrow.setOnClickListener {
            finish()
        }

        resetPasswordBtn.setOnClickListener {

            val layout : ScrollView = findViewById(R.id.forgotPassword_layout)

            if(NetworkHelper.isNetworkConnected(this)){
                var email: String = emailEt.text.toString()
                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(layout,"Please enter email id",
                        Snackbar.LENGTH_SHORT).show()
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Snackbar.make(layout,"Invalid Email",
                        Snackbar.LENGTH_SHORT).show()
                }
                else {
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(this, OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                finish()
                                Snackbar.make(layout,"Reset link sent to your email",
                                    Snackbar.LENGTH_LONG).show()
                            } else {
                                Snackbar.make(layout,"Unable to send reset mail",
                                    Snackbar.LENGTH_LONG).show()
                            }
                        })
                }
            }else{
                Snackbar.make(layout,"Sorry! There is no network connection.Please try later",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}