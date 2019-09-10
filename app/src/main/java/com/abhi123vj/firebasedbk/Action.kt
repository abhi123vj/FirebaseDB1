package com.abhi123vj.firebasedbk

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.Intent.parseIntent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_action.*
import kotlinx.android.synthetic.main.layout_list.view.*
import org.w3c.dom.Comment
import java.io.File
import java.nio.file.Paths
import java.util.jar.Manifest

class Action : AppCompatActivity() {


    lateinit var storagereff : StorageReference
    lateinit var databasereff : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        storagereff = FirebaseStorage.getInstance().getReference()
        databasereff = FirebaseDatabase.getInstance().getReference("uploads")



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        button3.setOnClickListener {

            getpdf()

        }


    }

    private fun getpdf(){
        val intent = Intent().setType("application/pdf").setAction(ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent,"Select PDF"),1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1 && resultCode== Activity.RESULT_OK&&data!=null &&data.getData()!=null)
          uploadPDFfile(data.getData())

    }

    private fun uploadPDFfile(data: Uri) {
        val reference = storagereff.child("uploads/"+System.currentTimeMillis()+".pdf")
        reference.putFile(data)
            .addOnSuccessListener {



            }

            .addOnProgressListener {

            }
    }



}









