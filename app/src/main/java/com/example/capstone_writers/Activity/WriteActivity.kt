package com.example.capstone_writers.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.capstone_writers.DataModel.PostModel
import com.example.capstone_writers.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WriteActivity : AppCompatActivity() {
    var backKeyPressedTime : Long = 0
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        auth = Firebase.auth
        val database = Firebase.database.reference

        val categorySpinner: Spinner = findViewById(R.id.CategorySpinner)
        ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        val postSpinner: Spinner = findViewById(R.id.PostSpinner)
        ArrayAdapter.createFromResource(this, R.array.post_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            postSpinner.adapter = adapter
        }

        val reqSaveBtn = findViewById<Button>(R.id.ReqSaveBtn)
        val titleEdit = findViewById<EditText>(R.id.TitleEdit)
        val contentsEdit = findViewById<EditText>(R.id.ContentsEdit)
        val homeBtn = findViewById<ImageView>(R.id.HomeBtn)
        val searchBtn = findViewById<ImageView>(R.id.SearchBtn)
        val myPageBtn = findViewById<ImageView>(R.id.MyPageBtn)

        reqSaveBtn.setOnClickListener {
            // 글 타입 지정 확인
            if (postSpinner.selectedItem.toString() != "글 타입 지정") {
                // 카테고리 지정 확인
                if (categorySpinner.selectedItem.toString() != "카테고리 지정") {
                    // 제목 확인
                    if (titleEdit.text.isNotBlank()) {
                        // 내용 확인
                        if (contentsEdit.text.isNotBlank()) {

                            val dDialogView = LayoutInflater.from(this).inflate(R.layout.apply_check, null)
                            val dBuilder = AlertDialog.Builder(this).setView(dDialogView)

                            val dAlertDialog = dBuilder.show()

                            val dApplyBtn = dAlertDialog.findViewById<Button>(R.id.ApplyBtn)
                            val dCancelBtn = dAlertDialog.findViewById<Button>(R.id.CancleBtn)

                            dApplyBtn!!.setOnClickListener {

                                val dateNow = Date(System.currentTimeMillis())
                                val dateFormat = SimpleDateFormat("yyyy.MM.dd kk:mm:ss", Locale("ko", "KR"))

                                var postType = ""
                                postType = if (postSpinner.selectedItem.toString() == "포스트") {
                                    "post"
                                } else {
                                    "request"
                                }

                                database.child("userInfo").child(auth.currentUser!!.uid).child("userNickname").get().addOnSuccessListener {
                                    val postModel = PostModel(auth.currentUser!!.uid, categorySpinner.selectedItem.toString(),
                                        titleEdit.text.toString(), contentsEdit.text.toString(), it.value.toString(), dateFormat.format(dateNow))
                                    database.child(postType).push().setValue(postModel)
                                }
                                val intent1 = Intent(this, FirstMainPageActivity::class.java)
                                startActivity(intent1)
                                overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
                            }
                            dCancelBtn!!.setOnClickListener {
                                dAlertDialog.dismiss()
                            }

                        } else {
                            Toast.makeText(this, "내용을 작성해주세요.", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this, "제목을 작성해주세요.", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "카테고리를 지정해주세요.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "글 타입을 지정해주세요.", Toast.LENGTH_SHORT).show()
            }


        }

        homeBtn.setOnClickListener {
            val intent1 = Intent(this, FirstMainPageActivity::class.java)
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