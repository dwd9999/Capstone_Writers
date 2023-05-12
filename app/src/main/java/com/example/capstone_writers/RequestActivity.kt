package com.example.capstone_writers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.capstone_writers.databinding.ActivityRequestBinding

// 신청서 화면 구현
class RequestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_request)

        binding.backbtn.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }
}