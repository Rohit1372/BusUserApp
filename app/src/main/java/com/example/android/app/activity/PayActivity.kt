package com.example.android.app.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android.app.R
import com.example.android.app.utils.NetworkHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PayActivity : AppCompatActivity() , PaymentResultListener {

    lateinit var pay_name :EditText
    lateinit var pay_email : EditText
    lateinit var pay_phoneNo : EditText
    lateinit var pay_noOfSeats :TextView
    lateinit var pay_selected_seats :TextView
    lateinit var pay_total_price : TextView
    lateinit var stop_loc : TextView
    lateinit var pay :Button

    private lateinit var auth: FirebaseAuth

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        pay_name = findViewById(R.id.pay_name)
        pay_email = findViewById(R.id.pay_email)
        pay_phoneNo = findViewById(R.id.pay_phone_no)
        pay_noOfSeats = findViewById(R.id.pay_noOfSeat1)
        pay_selected_seats = findViewById(R.id.pay_selectedSeats1)
        pay_total_price = findViewById(R.id.pay_totalPrice1)
        stop_loc = findViewById(R.id.pay_stop_loc)
        pay = findViewById(R.id.pay)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Payment"

        val intent_noOfSeat = intent.getStringExtra("noOfSeats").toString()
        val intent_selectedseats = intent.getStringExtra("selectedSeats").toString()
        val intent_totalPrice = intent.getStringExtra("totalPrice").toString()

        pay_total_price.text = intent_totalPrice
        pay_selected_seats.text = intent_selectedseats
        pay_noOfSeats.text = intent_noOfSeat

        val layout : ScrollView = findViewById(R.id.pay_layout)



        pay.setOnClickListener {

            if(NetworkHelper.isNetworkConnected(this)){

                val pname = pay_name.text.toString()
                val pemail = pay_email.text.toString()
                val pPhoneNo = pay_phoneNo.text.toString()
                val pStopLoc = stop_loc.text.toString()

                if(TextUtils.isEmpty(pname) || TextUtils.isEmpty(pemail) || TextUtils.isEmpty(pPhoneNo)|| TextUtils.isEmpty(pStopLoc)){
                    Snackbar.make(layout,"Please fill all the fiels",
                        Snackbar.LENGTH_LONG).show()
                }else if(!Patterns.EMAIL_ADDRESS.matcher(pemail).matches()){
                    Snackbar.make(layout,"Invalid Email",
                        Snackbar.LENGTH_LONG).show()
                }else if(pPhoneNo.length < 10){
                    Snackbar.make(layout,"Invlid phone number",
                        Snackbar.LENGTH_LONG).show()
                }
                else{
                    //saveFireStore(name,phone,email,noOfSeats,selectedSeats,totalPrice,id)
                    makepayment()
                }

            }else{
                Snackbar.make(layout,"Sorry! There is no network connection.Please try later",
                    Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun makepayment(){
        val co = Checkout()
        val price = Integer.parseInt(pay_total_price.text.toString())
        val amount =price*100
        val email = pay_email.text.toString()
        val phone = pay_phoneNo.text.toString()
        try {
            val options = JSONObject()
            options.put("name","My Bus")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            //options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","$amount")//pass amount in currency subunits

            val retryObj =  JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","$email")
            prefill.put("contact","$phone")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful", Toast.LENGTH_LONG).show()


        val intent_from = intent.getStringExtra("from").toString()
        val intent_to = intent.getStringExtra("to").toString()
        val intent_busService = intent.getStringExtra("bus service").toString()
        val intent_busNumber = intent.getStringExtra("bus no.").toString()
        val intent_date = intent.getStringExtra("date").toString()
        val intent_startTime = intent.getStringExtra("start time").toString()
        val intent_arrivalTime = intent.getStringExtra("arrival time").toString()

        val id = intent.getStringExtra("id").toString()
        val name = pay_name.text.toString()
        val phone = pay_phoneNo.text.toString()
        val email = pay_email.text.toString()
        val noOfSeats = pay_noOfSeats.text.toString()
        val selectedSeats = pay_selected_seats.text.toString()
        val totalPrice = pay_total_price.text.toString()

        val intent = Intent(this,PaymentSuccessActivity::class.java)
        startActivity(intent)

        saveInBus(name,phone,email,noOfSeats,selectedSeats,totalPrice,id)

        saveInUser(name,phone,email,noOfSeats,selectedSeats,totalPrice,intent_from,intent_to,intent_busService,intent_busNumber,intent_date,intent_startTime,intent_arrivalTime)


        //Notification
        createNotifChannel()

        val notif = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Ticked Booked")
            .setColor(2762355)
            .setContentText("Your Bus Ticket Booked Successfull")
            .setSmallIcon(R.drawable.notif_bus)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notifManger = NotificationManagerCompat.from(this)

        notifManger.notify(NOTIF_ID,notif)

        finish()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Error $p1", Toast.LENGTH_LONG).show()
    }

    private fun saveInBus(name : String, phone : String, email : String, noOfSeats : String, selectedSeats : String, totalPrice : String, id:String) {
        val db = Firebase.firestore
        val seat: MutableMap<String, Any> = HashMap()
        seat["Name"] = name
        seat["Phone No."] = phone
        seat["Email"] = email
        seat["Number Of Seats"] = noOfSeats
        seat["Selected Seats"] = selectedSeats
        seat["Total Price"] = totalPrice

        db.collection("Buses").document(id)
            .update("BookedSeats", FieldValue.arrayUnion(seat))
            .addOnCompleteListener {
                Toast.makeText(this, "Seat Booked successfully", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed To seat book", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveInUser(
        name: String,
        phone: String,
        email: String,
        noOfSeats: String,
        selectedSeats: String,
        totalPrice: String,
        from: String,
        to: String,
        busService: String,
        busNumber: String,
        date: String,
        startTime: String,
        arrivalTime: String,) {
        val db = Firebase.firestore
        val currentUser = auth.currentUser!!.uid
        val user: MutableMap<String, Any> = HashMap()
        user["Name"] = name
        user["Phone No."] = phone
        user["Email"] = email
        user["Number Of Seats"] = noOfSeats
        user["Selected Seats"] = selectedSeats
        user["From"] = from
        user["To"] = to
        user["Bus Service"] = busService
        user["Bus Number"] = busNumber
        user["Date"] = date
        user["Start Time"] = startTime
        user["Arrival Time"] = arrivalTime
        user["Total Price"] = totalPrice


        db.collection("UserProfile").document(currentUser)
            .update("BookedSeats", FieldValue.arrayUnion(user))
            .addOnCompleteListener {
                Toast.makeText(this, "Seat Booked successfully", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed To seat book", Toast.LENGTH_SHORT).show()
            }
    }

}