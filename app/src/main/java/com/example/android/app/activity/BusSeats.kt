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

class BusSeats : AppCompatActivity(), PaymentResultListener {

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

        var priceIntent = intent.getStringExtra("price").toString()
        ticketPrice.text = priceIntent

        //Total Seats
        //var seat = 32

        val recyclerView : RecyclerView = findViewById(R.id.seats_recyclerView)
        val adapter = SeatsAdapter(this,fetch(),ticketPrice,noOfSeats,selectedSeats,totalPrice,confirmBtn)
        recyclerView.layoutManager = GridLayoutManager(this,4)
        recyclerView.adapter=adapter

        confirmBtn.isEnabled = false

        confirmBtn.setOnClickListener {
            makepayment()
        }


    }

    private fun fetch() : ArrayList<String>{
        val item = ArrayList<String>()
        for(i in 1 until 33){
            item.add("$i")
        }
        return item
    }

    fun makepayment(){
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","My Bus")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits

            /*val retryObj =  JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);*/

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful",Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Error $p1",Toast.LENGTH_LONG).show()
    }

}