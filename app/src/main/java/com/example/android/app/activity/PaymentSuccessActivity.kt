package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.android.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PaymentSuccessActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference?=null
    var database : FirebaseDatabase?=null
    private lateinit var auth: FirebaseAuth

    lateinit var lottieCheckedDone : LottieAnimationView

    lateinit var book_now : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        supportActionBar?.hide()

        book_now = findViewById(R.id.book_now)

        val id = intent.getStringExtra("id").toString()
        val name = intent.getStringExtra("name").toString()
        val phone = intent.getStringExtra("phone").toString()
        val email = intent.getStringExtra("email").toString()
        val noOfSeats = intent.getStringExtra("noOfSeats").toString()
        val selectedSeats = intent.getStringExtra("selectedSeats").toString()
        val totalPrice = intent.getStringExtra("totalPrice").toString()

        val from = intent.getStringExtra("from").toString()
        val to = intent.getStringExtra("to").toString()
        val busService = intent.getStringExtra("bus service").toString()
        val busNumber = intent.getStringExtra("bus no.").toString()
        val date = intent.getStringExtra("date").toString()
        val startTime = intent.getStringExtra("start time").toString()
        val arrivalTime = intent.getStringExtra("arrival time").toString()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        book_now.setOnClickListener {

            val currentUser = auth.currentUser
            val userId = databaseReference?.push()?.key
            val currentUserDb = databaseReference?.child((currentUser?.uid!!))?.child("BookedBus")?.child(userId.toString())
            currentUserDb?.child("name")?.setValue(name)
            currentUserDb?.child("phoneNo")?.setValue(phone)
            currentUserDb?.child("email")?.setValue(email)
            currentUserDb?.child("number of seats")?.setValue(noOfSeats)
            currentUserDb?.child("selected seats")?.setValue(selectedSeats)
            currentUserDb?.child("total price")?.setValue(totalPrice)
            currentUserDb?.child("from")?.setValue(from)
            currentUserDb?.child("to")?.setValue(to)
            currentUserDb?.child("bus service")?.setValue(busService)
            currentUserDb?.child("bus number")?.setValue(busNumber)
            currentUserDb?.child("date")?.setValue(date)
            currentUserDb?.child("start time")?.setValue(startTime)
            currentUserDb?.child("arrival time")?.setValue(arrivalTime)

            saveFireStore(name,phone,email,noOfSeats,selectedSeats,totalPrice,id)

            Toast.makeText(this, "Book Now", Toast.LENGTH_SHORT).show()
        }

        lottieCheckedDone = findViewById(R.id.lottie_checked_done)
        lottieCheckedDone.speed = 1F
        lottieCheckedDone.playAnimation()
    }

    private fun saveFireStore(name : String, phone : String, email : String, noOfSeats : String, selectedSeats : String, totalPrice : String, id:String) {
        val db = Firebase.firestore
        val seat: MutableMap<String, Any> = HashMap()
        seat["Name"] = name
        seat["Phone No."] = phone
        seat["Email"] = email
        seat["Number Of Seats"] = noOfSeats
        seat["Selected Seats"] = selectedSeats
        seat["Total Price"] = totalPrice

        db.collection("Buses").document(id)
            .update("BookedSeats", FieldValue.arrayUnion(seat))
            .addOnCompleteListener {
                Toast.makeText(this, "Seat Booked successfully", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed To seat book", Toast.LENGTH_SHORT).show()
            }
    }
}