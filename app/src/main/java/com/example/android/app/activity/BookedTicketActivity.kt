package com.example.android.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.app.adapter.BookedTicketAdapter
import com.example.android.app.model.BookedTicket
import com.example.android.app.utils.NetworkHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookedTicketActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookedTicketlist : ArrayList<BookedTicket>
    private lateinit var bookedTicketAdapter: BookedTicketAdapter

    var databaseReference: DatabaseReference?=null
    var database : FirebaseDatabase?=null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_ticket)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Booked buses"

        val id : String = intent.getStringExtra("id").toString()

        recyclerView = findViewById(R.id.recyclerView_bookedTicket)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid

        bookedTicketlist = ArrayList()
        bookedTicketAdapter = BookedTicketAdapter(this,bookedTicketlist,currentUser)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookedTicketAdapter

//        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                bookedTicketAdapter.deleteItem(viewHolder.adapterPosition)
            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        getData()

    }

    fun getData(){
        val db =  Firebase.firestore
        val currentUser = auth.currentUser!!.uid
        val id : String = intent.getStringExtra("id").toString()

        db.collection("UserProfile")
            .document(currentUser)
            .get()
            .addOnSuccessListener {
                val result: MutableMap<String, Any>? = it.data
                val bookeddata = result?.get("BookedSeats")
                if(bookeddata!=null){
                    val seat = bookeddata as ArrayList<*>
                    for (values in seat) {
                        var data = values as MutableMap<*, *>
                        bookedTicketlist.add(
                            BookedTicket(
                                data["Name"] as String,
                                data["Phone No."] as String,
                                data["Email"] as String,
                                data["Number Of Seats"] as String,
                                data["Selected Seats"] as String,
                                data["Bus Number"] as String,
                                data["Bus Service"] as String,
                                data["Date"] as String,
                                data["Start Time"] as String,
                                data["Arrival Time"] as String,
                                data["Total Price"] as String,
                                data["From"] as String,
                                data["To"] as String,
                            )
                        )
                    }
                    bookedTicketAdapter.notifyDataSetChanged()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
    }
}