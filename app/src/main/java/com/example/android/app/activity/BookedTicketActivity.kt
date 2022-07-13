package com.example.android.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.app.adapter.BookedTicketAdapter
import com.example.android.app.model.BookedTicket
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

        bookedTicketlist = ArrayList()
        bookedTicketAdapter = BookedTicketAdapter(this,bookedTicketlist)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookedTicketAdapter

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)?.child("BookedBus")

        userReference?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(userData in snapshot.children){
                            val user = userData.getValue(BookedTicket::class.java)
                            bookedTicketlist.add(user!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )




    }
}