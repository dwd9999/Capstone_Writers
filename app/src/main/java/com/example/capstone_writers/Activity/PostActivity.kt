package com.example.capstone_writers.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.capstone_writers.R

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val uid = intent.getStringExtra("uid")
        val type = intent.getStringExtra("type")
        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")
        val nickname = intent.getStringExtra("nickname")
        val date = intent.getStringExtra("date")

        val typeText = findViewById<TextView>(R.id.TypeText)
        val categoryText = findViewById<TextView>(R.id.CategoryText)
        val titleText = findViewById<TextView>(R.id.TitleText)
        val contentsText = findViewById<TextView>(R.id.ContentsText)
        val nicknameText = findViewById<TextView>(R.id.NicknameText)
        val dateText = findViewById<TextView>(R.id.DateText)
        val commissionBtn = findViewById<Button>(R.id.CommissionBtn)

        typeText.text = type
        categoryText.text = category
        titleText.text = title
        contentsText.text = contents
        nicknameText.text = nickname
        dateText.text = date

        if (type == "포스트") {
            commissionBtn.text = "커미션 신청"
        } else {
            commissionBtn.text = "커미션 지원"
        }

        commissionBtn.setOnClickListener {
            if (type == "포스트") {
                val intent1 = Intent(this, RequestActivity::class.java)
                startActivity(intent1)
            } else {
                val intent2 = Intent(this, ApplicateActivity::class.java)
                startActivity(intent2)
            }
        }

        nicknameText.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }
    }
}