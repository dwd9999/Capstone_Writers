package com.example.capstone_writers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post)

        val btn = findViewById<Button>(R.id.applibtn)
        btn.setOnClickListener{
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }
    }
}