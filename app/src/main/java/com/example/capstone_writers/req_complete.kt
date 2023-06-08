package com.example.capstone_writers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.capstone_writers.Activity.FirstMainPageActivity
import com.example.capstone_writers.Activity.MainActivity

class req_complete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_req_complete)

        Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, FirstMainPageActivity::class.java))
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
                finish()
        }, 3000)
    }
}