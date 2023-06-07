package com.example.capstone_writers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Post2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post2)

        val btn = findViewById<Button>(R.id.applibtn2)
        btn.setOnClickListener {
            val intent = Intent(this, ApplicateActivity::class.java)
            startActivity(intent)
        }
    }
}