package com.example.android.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.busadminapp.adapter.RouteAdapter
import com.example.android.busadminapp.model.Route
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Routes : AppCompatActivity() {

    private lateinit var textFrom : TextView
    private lateinit var textTo : TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var routeList : ArrayList<Route>
    private lateinit var routeAdapter: RouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "View Routes"

        textFrom = findViewById(R.id.textFrom)
        textTo = findViewById(R.id.textTo)

        val id : String = intent.getStringExtra("id").toString()

        val fromR = intent.getStringExtra("from").toString()
        val toR = intent.getStringExtra("to").toString()

        textFrom.text = fromR
        textTo.text = toR


        //addRoute = findViewById(R.id.addRoute)
        recyclerView = findViewById(R.id.recyclerView)

        routeList = ArrayList()
        routeAdapter = RouteAdapter(this,routeList,id)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = routeAdapter

        val db =  Firebase.firestore

        db.collection("Buses")
            .document(id)
            .get()
            .addOnSuccessListener {
            val result: MutableMap<String, Any>? = it.data
                val routesData = result?.get("Routes")
            if(routesData!=null){
                val routes = routesData as ArrayList<*>
                for (values in routes) {
                    var data = values as MutableMap<*, *>
                    routeList.add(
                        Route(
                            data["Stop Number"] as String,
                            data["Stop At"] as String,
                        )
                    )
                }
                routeAdapter.notifyDataSetChanged()
            }
            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }

    }
}