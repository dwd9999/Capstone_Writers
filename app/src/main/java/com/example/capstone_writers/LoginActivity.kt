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

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Press Login Button
        val loginBtn = findViewById<Button>(R.id.LoginBtn)
        val idEdit = findViewById<EditText>(R.id.IDEdit)
        val pwEdit = findViewById<EditText>(R.id.PWEdit)
        loginBtn.setOnClickListener {
            // Email Validation Check
            if(emailValidate(idEdit.text.toString())) {
                auth.signInWithEmailAndPassword(idEdit.text.toString(), pwEdit.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser

                        } else {
                            Toast.makeText(baseContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()

                        }
                    }
            } else {
                Toast.makeText(this, "유효한 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // Press Back Button
        val backBtn = findViewById<Button>(R.id.BackBtn)
        backBtn.setOnClickListener {
            finish()
        }
    }

    // Email Validation Check
    private fun emailValidate(string: String): Boolean {
        return string.matches("[a-zA-Z0-9._-]+@[a-z]+[.]+[a-z]+".toRegex())
    }
}