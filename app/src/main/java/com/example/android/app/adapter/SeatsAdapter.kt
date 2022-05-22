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
import com.example.android.app.activity.PayActivity

class SeatsAdapter(private val context:Context, private val item: ArrayList<String>,val ticketPrice:TextView,val noOfSeats : TextView,val selectedSeats:TextView,val totalPrice:TextView,val confirmBtn:TextView) :RecyclerView.Adapter<SeatsAdapter.ViewHolder>() {

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
            noOfSeats.setText("$count")

            var tp1 = ticketPrice.text.toString()
            var tp2 = Integer.parseInt(tp1)
            var tp3 = count*tp2
            totalPrice.setText("$tp3")

            /*var a  = noOfSeats.text.toString()
            if(Integer.parseInt(a) > 0){
                confirmBtn.isEnabled = true
            }else{
                confirmBtn.isEnabled = false
            }*/

            val po = position+1
            map.remove(po)
            selectedSeats.text = map.values.toString().replace("[","").replace("]","")

            val tP = totalPrice.text.toString()
            val nOS = noOfSeats.text.toString()
            val sS = selectedSeats.text.toString()
            confirmBtn.setOnClickListener {
                var c = noOfSeats.text.toString()
                if(Integer.parseInt(c) > 0){
                    val i = Intent(context,PayActivity::class.java)
                        .putExtra("totalPrice",tP)
                        .putExtra("noOfSeats",nOS)
                        .putExtra("selectedSeats",sS)
                    context.startActivity(i)
                }else{
                    Toast.makeText(context,"Please select your seat",Toast.LENGTH_LONG).show()
                }
            }

        }

        holder.seatText2.setOnClickListener {
            holder.seatText2.visibility = View.GONE
            holder.seatText.visibility = View.VISIBLE

            count++
            noOfSeats.setText("$count")

            var tp1 = ticketPrice.text.toString()
            var tp2 = Integer.parseInt(tp1)
            var tp3 = count*tp2
            totalPrice.setText("$tp3")

            /*var a  = noOfSeats.text.toString()
            if(Integer.parseInt(a) > 0){
                confirmBtn.isEnabled = true

            }else{
                confirmBtn.isEnabled = false
            }
*/

            val po = position+1
            map.put(po,"$po")
            selectedSeats.text = map.values.toString().replace("[","").replace("]","")

            val tP = totalPrice.text.toString()
            val nOS = noOfSeats.text.toString()
            val sS = selectedSeats.text.toString()
            confirmBtn.setOnClickListener {
                var c = noOfSeats.text.toString()
                if(Integer.parseInt(c) > 0){
                    val i = Intent(context,PayActivity::class.java)
                        .putExtra("totalPrice",tP)
                        .putExtra("noOfSeats",nOS)
                        .putExtra("selectedSeats",sS)
                    context.startActivity(i)
                }else{
                    Toast.makeText(context,"Please select your seat",Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return item.size
    }
}