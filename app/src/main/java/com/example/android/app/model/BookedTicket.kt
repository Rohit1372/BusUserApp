package com.example.android.app.model

data class BookedTicket(
    val name :String,
    val phoneNo :String,
    val email : String,
    val noOfSeats : String,
    val selectedSeats : String,
    val busNumber : String,
    val busService : String,
    val date : String,
    val startTime : String,
    val arrivalTime : String,
    val totalPrice : String,
    val from : String,
    val to : String,
)
