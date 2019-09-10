package com.abhi123vj.firebasedbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        button2.setOnClickListener {
            signupfn()
        }
    }

    private fun signupfn()
    {
        database = FirebaseDatabase.getInstance().reference
        if(editText7.text.toString().isEmpty()) {
            editText7.error = "Please enter your Username"
            editText7.requestFocus()
            return
        }
        if(editText4.text.toString().isEmpty()) {
            editText4.error = "Please enter your Email"
            editText4.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher( editText4.text.toString()).matches()) {
            editText4.error = "Email not valid"
            editText4.requestFocus()
            return
        }


        if(editText5.text.toString().isEmpty()) {
            editText5.error = "Please enter your password"
            editText5.requestFocus()
            return
        }

        if(editText6.text.toString().isEmpty()) {
            editText6.error = "Please re-enter your Password"
            editText6.requestFocus()
            return
        }

        if(editText6.text.toString()!=editText5.text.toString()) {
            editText6.error = "Password did not match"
            editText6.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(editText4.text.toString(), editText5.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }
                        }

                    writeNewUser("${editText7.text}","${user?.uid}","${user?.email}","${editText5.text}")


                } else {
                   Toast.makeText(baseContext,"sign up failed try again later",Toast.LENGTH_SHORT).show()
                    Log.d("Signup",task.toString())
                }

                // ...
            }




    }
    private fun writeNewUser( name: String,userId: String, email: String, pass:String) {
        val user = User(name, email,pass)
        database.child("users").child(userId).setValue(user)
    }
}
