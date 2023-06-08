package com.example.capstone_writers.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.capstone_writers.MyPagerAdapter
import com.example.capstone_writers.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirstMainPageActivity : AppCompatActivity() {
    var backKeyPressedTime : Long = 0
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_main_page)

        auth = Firebase.auth
        val database = Firebase.database.reference

        var tabsMain = findViewById<TabLayout>(R.id.TabsMain)
        var viewpagerMain = findViewById<ViewPager>(R.id.ViewpagerMain)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpagerMain.adapter = fragmentAdapter

        tabsMain.setupWithViewPager(viewpagerMain)

        val writeBtn = findViewById<ImageView>(R.id.WriteBtn)
        val searchBtn = findViewById<ImageView>(R.id.SearchBtn)
        val myPageBtn = findViewById<ImageView>(R.id.MyPageBtn)

        writeBtn.setOnClickListener {
            val intent1 = Intent(this, WriteActivity::class.java)
            startActivity(intent1)
            overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
        }
        searchBtn.setOnClickListener {
            val intent2 = Intent(this, SearchActivity::class.java)
            startActivity(intent2)
            overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
        }
        myPageBtn.setOnClickListener {
            val intent3 = Intent(this, MyPageActivity::class.java)
            intent3.putExtra("uid", Firebase.auth.currentUser!!.uid)
            startActivity(intent3)
            overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
        }


        this.onBackPressedDispatcher.addCallback(this, callback)

    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis()
                toast()
                return
            } else {
                finishAffinity()
            }
        }
    }
    fun toast(){
        Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }
}