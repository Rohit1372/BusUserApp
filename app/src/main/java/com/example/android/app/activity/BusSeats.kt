package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.app.adapter.SeatsAdapter
import org.w3c.dom.Text

class BusSeats : AppCompatActivity() {

    lateinit var totalSeats : TextView
    lateinit var remainingSeats : TextView
    lateinit var selectedSeats : TextView
    lateinit var price : TextView
    lateinit var confirmBtn : Button

    lateinit var addSeats : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_seats)

        totalSeats = findViewById(R.id.total_seats)
        remainingSeats = findViewById(R.id.seats_remaining)
        selectedSeats = findViewById(R.id.selected_seats)
        price = findViewById(R.id.price)
        confirmBtn = findViewById(R.id.confirmBtn)

        addSeats = findViewById(R.id.addSeats)

        var priceIntent = intent.getStringExtra("price").toString()
        price.text = priceIntent

        //Total Seats
        var seat = 32
        totalSeats.setText("$seat")

        val recyclerView : RecyclerView = findViewById(R.id.seats_recyclerView)
        val adapter = SeatsAdapter(this,fetch(),seat,selectedSeats,remainingSeats,confirmBtn,price,addSeats)
        recyclerView.layoutManager = GridLayoutManager(this,4)
        recyclerView.adapter=adapter

        confirmBtn.isEnabled = false


    }

    private fun fetch() : ArrayList<String>{
        val item = ArrayList<String>()
        for(i in 1 until 33){
            item.add("$i")
        }
        return item
    }

}