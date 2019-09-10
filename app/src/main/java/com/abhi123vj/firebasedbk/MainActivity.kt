package com.abhi123vj.firebasedbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()



        tv.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
            finish()
        }

        button.setOnClickListener {
            login()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?){

        if(currentUser!= null) {
            if(currentUser.isEmailVerified) {
                startActivity(Intent(this, Action::class.java))
                finish()
            }
            else
                Toast.makeText(baseContext, "Verify your Email",Toast.LENGTH_LONG).show()
        }
       // else
          //  Toast.makeText(baseContext, "Login Failed",Toast.LENGTH_SHORT).show()


    }

    private fun login(){

        if(editText.text.toString().isEmpty()) {
            editText.error = "Please enter your Email"
            editText.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher( editText.text.toString()).matches()) {
            editText.error = "Email not valid"
            editText.requestFocus()
            return
        }


        if(editText2.text.toString().isEmpty()) {
            editText2.error = "Please enter your password"
            editText2.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editText.text.toString(),editText2.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    database = FirebaseDatabase.getInstance().reference

                    database.child("users").child("/${user?.uid}/verification").setValue("YES")
                        .addOnSuccessListener {
                            // Write was successful!
                            // ... for updating specific node
                        }

                } else {


                    Toast.makeText(baseContext, "Wrong Email or password",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }



    }



}
