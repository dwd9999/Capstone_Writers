package com.example.capstone_writers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.capstone_writers.databinding.ActivityRequestBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// 신청서 화면 구현
class RequestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_request)

        binding.backbtn.setOnClickListener {
            val intent1 = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        //신청하기 버튼 클릭 시 각 항목이 데이터베이스에 저장
        binding.reqsaveBtn.setOnClickListener{

            val reqUsername = findViewById<EditText>(R.id.reqUsername).text.toString()
            val reqUseremail = findViewById<EditText>(R.id.reqUseremail).text.toString()
            val reqUserphone = findViewById<EditText>(R.id.reqUserphone).text.toString()
            val reqSize = findViewById<EditText>(R.id.reqSize).text.toString()
            val reqExp = findViewById<EditText>(R.id.reqExp).text.toString()
            val reqCharExp = findViewById<EditText>(R.id.reqCharExp).text.toString()

            val database = Firebase.database
            val myRef = database.getReference("Request_info")

            val model = DataModel(reqUsername, reqUseremail, reqUserphone, reqSize, reqExp, reqCharExp)
            myRef
                .push()
                .setValue(model)

            //버튼 클릭 시 신청완료 페이지로 이동
            val intent2 = Intent(this, req_complete::class.java)
            startActivity(intent2)

        }
    }
}
