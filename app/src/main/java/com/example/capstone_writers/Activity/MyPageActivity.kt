package com.example.capstone_writers.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.capstone_writers.Activity.MainActivity
import com.example.capstone_writers.Activity.SearchActivity
import com.example.capstone_writers.Activity.WriteActivity
import com.example.capstone_writers.DataModel.PostModel
import com.example.capstone_writers.PostAdapter
import com.example.capstone_writers.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MyPageActivity : AppCompatActivity() {
    var backKeyPressedTime : Long = 0
    var uid : String = ""
    val dataModelList = mutableListOf<PostModel>()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        uid = intent.getStringExtra("uid").toString()

        auth = Firebase.auth
        val database = Firebase.database.reference

        val nicknameText = findViewById<TextView>(R.id.NicknameText)
        database.child("userInfo").child(uid!!).child("userNickname").get().addOnSuccessListener {
            nicknameText.text = it.value.toString()
        }

        val listView = findViewById<ListView>(R.id.PostList)
        val adapterPost = PostAdapter(dataModelList)
        listView.adapter = adapterPost

        dataModelList.clear()
        database.child("post").orderByChild("uid").equalTo(uid).addChildEventListener(object:
            ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                dataModelList.add(snapshot.getValue<PostModel>()!!)
                adapterPost.notifyDataSetChanged()
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
        adapterPost.notifyDataSetChanged()


        val logoutBtn = findViewById<Button>(R.id.LogoutBtn)
        if (uid != Firebase.auth.currentUser!!.uid) {
            logoutBtn.visibility = View.INVISIBLE
        } else {
            logoutBtn.visibility = View.VISIBLE
        }
        logoutBtn.setOnClickListener {

            val dDialogView = LayoutInflater.from(this).inflate(R.layout.logout_check, null)
            val dBuilder = AlertDialog.Builder(this).setView(dDialogView)

            val dAlertDialog = dBuilder.show()

            val dLogoutBtn = dAlertDialog.findViewById<Button>(R.id.LogoutBtn)
            val dCancelBtn = dAlertDialog.findViewById<Button>(R.id.CancleBtn)

            dLogoutBtn!!.setOnClickListener {
                Toast.makeText(this, "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show()
                Firebase.auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            dCancelBtn!!.setOnClickListener {
                dAlertDialog.dismiss()
            }
        }

        val homeBtn = findViewById<ImageView>(R.id.HomeBtn)
        val writeBtn = findViewById<ImageView>(R.id.WriteBtn)
        val searchBtn = findViewById<ImageView>(R.id.SearchBtn)

        homeBtn.setOnClickListener {
            val intent1 = Intent(this, FirstMainPageActivity::class.java)
            startActivity(intent1)
            overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
        }
        writeBtn.setOnClickListener {
            val intent2 = Intent(this, WriteActivity::class.java)
            startActivity(intent2)
            overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
        }
        searchBtn.setOnClickListener {
            val intent3 = Intent(this, SearchActivity::class.java)
            startActivity(intent3)
            overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
        }

        this.onBackPressedDispatcher.addCallback(this, callback)

    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (uid == Firebase.auth.currentUser!!.uid) {
                if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                    backKeyPressedTime = System.currentTimeMillis()
                    toast()
                    return
                } else {
                    finishAffinity()
                }
            } else {
                finish()
            }

        }
    }
    fun toast(){
        Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }
}