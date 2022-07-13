package com.example.android.app.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.android.app.R
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    lateinit var logOut : CardView
    lateinit var busList : CardView
    lateinit var bookedTicket : CardView
    lateinit var profile : CardView
    private lateinit var home_back_arrow : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        busList = findViewById(R.id.busList)
        bookedTicket = findViewById(R.id.booked_ticket)
        profile = findViewById(R.id.profile)
        logOut = findViewById(R.id.log_out)
        home_back_arrow = findViewById(R.id.home_back_arrow)

        home_back_arrow.setOnClickListener {
            onBackPressed()
        }

        bookedTicket.setOnClickListener {
            val intent = Intent(this,BookedTicketActivity::class.java)
            startActivity(intent)
        }

        busList.setOnClickListener {
            val intent = Intent(this,Buses::class.java)
            startActivity(intent)
        }

        profile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        logOut.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure!")
            builder.setMessage("Do you want LogOut?")
            builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface, i:Int ->
                auth.signOut()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            })
            builder.setNegativeButton("No",{ dialogInterface : DialogInterface, i:Int ->
            })
            builder.create()
            builder.show()
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close this app?")
        builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface, i:Int ->
            finish()
        })
        builder.setNegativeButton("No",{ dialogInterface : DialogInterface, i:Int ->
        })
        builder.create()
        builder.show()
    }
}