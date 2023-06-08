package com.example.capstone_writers.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.capstone_writers.DataModel.DataModel
import com.example.capstone_writers.R
import com.example.capstone_writers.app_complete
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ApplicateActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicate)


        val appsavebtn = findViewById<Button>(R.id.appsaveBtn)
        appsavebtn.setOnClickListener{

        //지원하기 버튼 클릭 시 각 항목이 데이터베이스에 저장
        appsavebtn.setOnClickListener {

            val appUseremail = findViewById<EditText>(R.id.appUseremail).text.toString()
            val appExpSum = findViewById<EditText>(R.id.appExpSum).text.toString()
            val appExpTime = findViewById<EditText>(R.id.appExpTime).text.toString()
            val appWriterIntro = findViewById<EditText>(R.id.appWriterIntro).text.toString()
            val appmemo = findViewById<EditText>(R.id.appmemo).text.toString()

            val database = Firebase.database
            val myRef = database.getReference("applicate_info")

            val model = DataModel(appUseremail, appExpSum, appExpTime, appWriterIntro, appmemo)
            myRef
                .push()
                .setValue(model)

            //버튼 클릭 시 지원완료 페이지로 이동
            val intent = Intent(this, app_complete::class.java)
            startActivity(intent)
            }

        }
    }
}