package com.example.android.app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import com.example.android.app.R
import com.example.android.app.utils.NetworkHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference?=null
    var database : FirebaseDatabase?=null

    private lateinit var auth: FirebaseAuth

    private lateinit var name : EditText
    private lateinit var phoneNo : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var registerBtn : Button
    private lateinit var loginBtn : TextView
    private lateinit var back_arrow : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        //Remove Action Bar
        supportActionBar?.hide()

        name = findViewById(R.id.name)
        phoneNo = findViewById(R.id.phoneNo)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        loginBtn  = findViewById(R.id.loginBtn)
        back_arrow = findViewById(R.id.back_arrow)

        back_arrow.setOnClickListener {
            finish()
        }


        loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users")

        register()

    }

    private  fun register(){
        registerBtn.setOnClickListener{

            val layout : ScrollView = findViewById(R.id.signUp_layout)

            if(NetworkHelper.isNetworkConnected(this)){

                var name:String = name.text.toString()
                var phone:String = phoneNo.text.toString()
                var email: String = email.text.toString()
                var password: String = password.text.toString()
                var confirmPassword: String = confirmPassword.text.toString()

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)  || TextUtils.isEmpty(confirmPassword)) {
                    Snackbar.make(layout,"Please fill all the fields",
                        Snackbar.LENGTH_LONG).show()
                } else if(password != confirmPassword){
                    Snackbar.make(layout,"Both password mismatch",
                        Snackbar.LENGTH_LONG).show()
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Snackbar.make(layout,"Invalid Email",
                        Snackbar.LENGTH_LONG).show()
                }else if(password.length<6){
                    Snackbar.make(layout,"Password must be atleast 6",
                        Snackbar.LENGTH_LONG).show()
                }else if(phone.length < 10){
                    Snackbar.make(layout,"Invalid Phone Number",
                        Snackbar.LENGTH_LONG).show()
                }
                else{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
                        if(task.isSuccessful){

                            //User profile
                            val currentUser = auth.currentUser!!.uid

                            val db = Firebase.firestore
                            val user : MutableMap<String,Any> = HashMap()

                            user["name"] = name
                            user["phoneNo"] = phone
                            user["email"] = email

                            db.collection("UserProfile").document(currentUser).set(user)
                                .addOnCompleteListener {

                                    Toast.makeText(this,"Profile added ",Toast.LENGTH_SHORT).show()

                                }.addOnFailureListener {
                                    Toast.makeText(this,"profile not added",Toast.LENGTH_SHORT).show()
                                }

                            Snackbar.make(layout,"Successfully Registered",
                                Snackbar.LENGTH_LONG).show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            Snackbar.make(layout,"Registration Failed",
                                Snackbar.LENGTH_LONG).show()
                        }
                    })
                }

            }else{
                Snackbar.make(layout,"Sorry! There is no network connection.Please try later",
                    Snackbar.LENGTH_SHORT).show()
            }

        }
    }

}