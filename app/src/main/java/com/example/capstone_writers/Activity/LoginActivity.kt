package com.example.capstone_writers.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_writers.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 파이어베이스 인증 시작
        auth = Firebase.auth

        val database = Firebase.database.reference

        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        val idEdit = findViewById<EditText>(R.id.IDEdit)
        val pwEdit = findViewById<EditText>(R.id.PWEdit)
        // 로그인 버튼
        loginBtn.setOnClickListener {
            // 이메일 유효성 확인
            if(emailValidate(idEdit.text.toString())) {
                auth.signInWithEmailAndPassword(idEdit.text.toString(), pwEdit.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            database.child("userInfo").child(auth.currentUser!!.uid).child("userNickname").get().addOnSuccessListener {
                                Toast.makeText(this, "환영합니다. ${it.value.toString()}님", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, FirstMainPageActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "유효한 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 뒤로가기 버튼
        val backBtn = findViewById<Button>(R.id.BackBtn)
        backBtn.setOnClickListener {
            finish()
        }
    }

    // 이메일 유효성 확인 함수
    private fun emailValidate(string: String): Boolean {
        return string.matches("[a-zA-Z0-9._-]+@[a-z]+[.]+[a-z]+".toRegex())
    }
}