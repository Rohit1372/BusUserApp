package com.example.android.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.app.model.BookedTicket

class BookedTicketAdapter(val context: Context, val bookedList : ArrayList<BookedTicket>):
    RecyclerView.Adapter<BookedTicketAdapter.BookedTicketAdapterViewHolder>(){

    class BookedTicketAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.bt_name)
        val phoneNo : TextView = view.findViewById(R.id.bt_phone)
        val email : TextView = view.findViewById(R.id.bt_Email)
        val noOfSeats : TextView = view.findViewById(R.id.bt_noOfSeats)
        val selectedSeats : TextView = view.findViewById(R.id.bt_selectedSeats)
        val totalPrice : TextView = view.findViewById(R.id.bt_price)
        val date : TextView = view.findViewById(R.id.bt_date)
        val startTime : TextView = view.findViewById(R.id.bt_startTime)
        val arrivalTime : TextView = view.findViewById(R.id.bt_arrivalTime)
        val from : TextView = view.findViewById(R.id.bt_from)
        val to : TextView = view.findViewById(R.id.bt_to)
        val busNumber : TextView = view.findViewById(R.id.bt_busNumber)
        val busService : TextView = view.findViewById(R.id.bt_busService)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedTicketAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.booked_ticket_single_item_view,parent,false)
        return BookedTicketAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookedTicketAdapterViewHolder, position: Int) {
        val list = bookedList[position]
        holder.name.text = list.name
        holder.phoneNo.text = list.phoneNo
        holder.email.text = list.email
        holder.noOfSeats.text = list.noOfSeats
        holder.selectedSeats.text = list.selectedSeats
        holder.totalPrice.text = list.totalPrice
        holder.date.text = list.date
        holder.startTime.text = list.startTime
        holder.arrivalTime.text = list.arrivalTime
        holder.from.text = list.from
        holder.to.text = list.to
        holder.busNumber.text = list.busNumber
        holder.busService.text = list.busService
    }

    override fun getItemCount(): Int {
        return bookedList.size
    }

}