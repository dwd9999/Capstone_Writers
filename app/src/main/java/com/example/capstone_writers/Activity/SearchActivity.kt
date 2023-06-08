package com.example.capstone_writers.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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

class SearchActivity : AppCompatActivity() {
    var backKeyPressedTime : Long = 0
    private lateinit var auth: FirebaseAuth
    val dataModelList = mutableListOf<PostModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        auth = Firebase.auth
        val database = Firebase.database.reference

        val postSpinner: Spinner = findViewById(R.id.PostSpinner)
        ArrayAdapter.createFromResource(this, R.array.simple_post_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            postSpinner.adapter = adapter
        }

        val listView = findViewById<ListView>(R.id.PostList)
        val adapterPost = PostAdapter(dataModelList)
        listView.adapter = adapterPost

        val searchInput = findViewById<EditText>(R.id.SearchInput)
        val searchImage = findViewById<ImageView>(R.id.SearchImage)

        searchImage.setOnClickListener {
            if (searchInput.text.isNotBlank()) {
                dataModelList.clear()
                var postType = ""
                postType = if (postSpinner.selectedItem.toString() == "포스트") {
                    "post"
                } else {
                    "request"
                }
                database.child(postType).orderByChild("title").equalTo(searchInput.text.toString()).addChildEventListener(object:
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

            } else {
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val clickedItem = listView.getItemAtPosition(i) as PostModel
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("uid", clickedItem.uid)
            intent.putExtra("type", postSpinner.selectedItem.toString())
            intent.putExtra("category", clickedItem.category)
            intent.putExtra("title", clickedItem.title)
            intent.putExtra("contents", clickedItem.contents)
            intent.putExtra("nickname", clickedItem.nickname)
            intent.putExtra("date", clickedItem.date)
            startActivity(intent)
        }

        val homeBtn = findViewById<ImageView>(R.id.HomeBtn)
        val writeBtn = findViewById<ImageView>(R.id.WriteBtn)
        val myPageBtn = findViewById<ImageView>(R.id.MyPageBtn)

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