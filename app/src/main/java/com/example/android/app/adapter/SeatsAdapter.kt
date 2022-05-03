package com.example.android.app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R

class SeatsAdapter(private val context:Context, private val item: ArrayList<String>,var seat:Int ,var selectedSeats : TextView,var remainingSeats:TextView,val confirmBtn:Button,val price : TextView,val addSeats : TextView) :RecyclerView.Adapter<SeatsAdapter.ViewHolder>() {

    var count = 0

    //val item2 = ArrayList<String>()

    //MutableMap
    val map : MutableMap<Int,String> = mutableMapOf<Int,String>()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val seatText : TextView = itemView.findViewById(R.id.seatText)
        val seatText2 : TextView = itemView.findViewById(R.id.seatText2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.seats_single_item_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position : Int) {
        holder.seatText.text = item[position]
        holder.seatText2.text = item[position]

        holder.seatText.setOnClickListener {
            //Toast.makeText(context,"${holder.textView.text}",Toast.LENGTH_SHORT).show()
            holder.seatText.visibility = View.GONE
            holder.seatText2.visibility = View.VISIBLE

            count--
            var rseat1 = seat - count
            selectedSeats.setText("$count")
            remainingSeats.setText("$rseat1")

            var a  = selectedSeats.text.toString()
            if(Integer.parseInt(a) > 0){
                confirmBtn.isEnabled = true
            }else{
                confirmBtn.isEnabled = false
            }

            val po = position+1
            map.remove(po)
            addSeats.text = map.values.toString().replace("[","").replace("]","")
        }

        holder.seatText2.setOnClickListener {
            //Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show()
            holder.seatText2.visibility = View.GONE
            holder.seatText.visibility = View.VISIBLE

            count++
            var rseat1 = seat - count
            selectedSeats.setText("$count")
            remainingSeats.setText("$rseat1")

            var a  = selectedSeats.text.toString()
            if(Integer.parseInt(a) > 0){
                confirmBtn.isEnabled = true

            }else{
                confirmBtn.isEnabled = false
            }

            /*val po = position+1
            item2.add("$po")
            addSeats.text = item2.toString().replace("[","").replace("]","")*/

            val po = position+1
            map.put(po,"$po")
            addSeats.text = map.values.toString().replace("[","").replace("]","")

        }

        confirmBtn.setOnClickListener {
            Toast.makeText(context,"Confirm",Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return item.size
    }
}