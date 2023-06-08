package com.example.capstone_writers.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import com.example.capstone_writers.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 파이어베이스 데이터베이스 접근
        auth = Firebase.auth
        val database = Firebase.database.reference

        // 스플래시 이미지 이동
        val splashImage = findViewById<ImageView>(R.id.SplashImage)
        ObjectAnimator.ofFloat(splashImage, "translationY", -340f).apply {
            duration = 2000
            start()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // 이미 로그인 정보가 있는 경우 바로 메인 페이지, 아닌 경우 로그인 페이지
            try {
                database.child("userInfo").child(auth.currentUser!!.uid).child("userNickname").get().addOnSuccessListener {
                    Toast.makeText(this, "환영합니다. ${it.value}님", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, FirstMainPageActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
                finish()
            }
        }, 3000)
    }
}