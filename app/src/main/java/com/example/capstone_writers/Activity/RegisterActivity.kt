package com.example.capstone_writers.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.capstone_writers.DataModel.UserInfoModel
import com.example.capstone_writers.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 파이어베이스 인증 시작
        auth = Firebase.auth
        val database = Firebase.database.reference

        val nameInput = findViewById<EditText>(R.id.NameInput)
        val nicknameInput = findViewById<EditText>(R.id.NicknameInput)
        val emailInput = findViewById<EditText>(R.id.EmailInput)
        val passwordInput = findViewById<EditText>(R.id.PasswordInput)
        val passwordcheckInput = findViewById<EditText>(R.id.PasswordcheckInput)

        val registerBtn = findViewById<Button>(R.id.RegisterBtn)
        registerBtn.setOnClickListener {
            // 빈 칸 확인
            if (nameInput.text.isNotBlank() && nicknameInput.text.isNotBlank() &&
                emailInput.text.isNotBlank() && passwordInput.text.isNotBlank() &&
                passwordcheckInput.text.isNotBlank()) {
                // 이메일 유효성 확인
                if (emailValidate(emailInput.text.toString())) {
                    // 비밀번호 유효성 확인
                    if (passwordInput.text.length > 8) {
                        // 비밀번호 확인
                        if (passwordInput.text.toString() == passwordcheckInput.text.toString()) {
                            // 회원 가입
                            auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        val infomodel = UserInfoModel(nameInput.text.toString(), nicknameInput.text.toString())
                                        database.child("userInfo").child(Firebase.auth.currentUser!!.uid).setValue(infomodel)
                                        Toast.makeText(this, "환영합니다. ${nicknameInput.text}님", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, FirstMainPageActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "가입 실패", Toast.LENGTH_SHORT).show()
                                    }

                                }

                        } else {
                            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this, "비밀번호는 최소 8자리입니다.", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "유효한 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "입력되지 않은 정보가 존재합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val cancelBtn = findViewById<Button>(R.id.CancleBtn)
        cancelBtn.setOnClickListener {
            finish()
        }
    }

    private fun emailValidate(string: String): Boolean {
        return string.matches("[a-zA-Z0-9._-]+@[a-z]+[.]+[a-z]+".toRegex())
    }
}