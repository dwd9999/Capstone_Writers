package com.example.capstone_writers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        val cancelBtn = findViewById<Button>(R.id.CancleBtn)
        cancelBtn.setOnClickListener {
            finish()
        }
    }
}