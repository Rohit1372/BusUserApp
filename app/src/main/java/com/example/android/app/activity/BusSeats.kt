package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.app.adapter.SeatsAdapter
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import org.w3c.dom.Text

class BusSeats : AppCompatActivity() {

    lateinit var ticketPrice : TextView
    lateinit var noOfSeats : TextView
    lateinit var selectedSeats : TextView
    lateinit var totalPrice : TextView
    lateinit var confirmBtn : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_seats)

        ticketPrice = findViewById(R.id.ticketPrice1)
        noOfSeats = findViewById(R.id.noOfSeat1)
        selectedSeats = findViewById(R.id.selectedSeats1)
        totalPrice = findViewById(R.id.totalPrice1)
        confirmBtn = findViewById(R.id.confirmBtn)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Book your seat"

        var fromBS = intent.getStringExtra("from").toString()
        var toBS = intent.getStringExtra("to").toString()
        var busServiceBS = intent.getStringExtra("bus service").toString()
        var busNumberBS = intent.getStringExtra("bus no.").toString()
        var dateBS = intent.getStringExtra("date").toString()
        var startTimeBS = intent.getStringExtra("start time").toString()
        var arrivalTimeBS = intent.getStringExtra("arrival time").toString()
        var priceIntent = intent.getStringExtra("price").toString()
        ticketPrice.text = priceIntent

        val id = intent.getStringExtra("id").toString()

        //Total Seats
        //var seat = 32

        val recyclerView : RecyclerView = findViewById(R.id.seats_recyclerView)
        val adapter = SeatsAdapter(this,fetch(),ticketPrice,noOfSeats,selectedSeats,totalPrice,confirmBtn,id,fromBS,toBS,busServiceBS,busNumberBS,dateBS,startTimeBS,arrivalTimeBS)
        recyclerView.layoutManager = GridLayoutManager(this,4)
        recyclerView.adapter=adapter

        //confirmBtn.isEnabled = false


    }

    private fun fetch() : ArrayList<String>{
        val item = ArrayList<String>()
        for(i in 1 until 33){
            item.add("$i")
        }
        return item
    }


}