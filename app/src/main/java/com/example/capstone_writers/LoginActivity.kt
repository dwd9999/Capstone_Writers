package com.example.capstone_writers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 파이어베이스 인증 시작
        auth = Firebase.auth

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
                            val user = auth.currentUser
                            // 사용자 인증 정보 전달 및 액티비티 전환 구현
                        } else {
                            Toast.makeText(baseContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
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