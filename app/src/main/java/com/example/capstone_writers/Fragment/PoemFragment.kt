package com.example.capstone_writers.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import com.example.capstone_writers.DataModel.PostModel
import com.example.capstone_writers.Activity.PostActivity
import com.example.capstone_writers.PostAdapter
import com.example.capstone_writers.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PoemFragment : Fragment() {
    val dataModelList = mutableListOf<PostModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val database = Firebase.database.reference
        val view = inflater.inflate(R.layout.fragment_poem, container, false)

        val postSpinner: Spinner = view.findViewById(R.id.PostSpinner)
        ArrayAdapter.createFromResource(container!!.context, R.array.simple_post_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            postSpinner.adapter = adapter
        }

        val listView = view.findViewById<ListView>(R.id.PostList)
        val adapterPost = PostAdapter(dataModelList)

        listView.adapter = adapterPost

        postSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var postType = ""
                postType = if (postSpinner.selectedItem.toString() == "포스트") {
                    "post"
                } else {
                    "request"
                }

                dataModelList.clear()
                database.child(postType).orderByChild("category").equalTo("시").addChildEventListener(object:ChildEventListener {
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
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }

        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val clickedItem = listView.getItemAtPosition(i) as PostModel
            val intent = Intent(container.context, PostActivity::class.java)
            intent.putExtra("uid", clickedItem.uid)
            intent.putExtra("type", postSpinner.selectedItem.toString())
            intent.putExtra("category", clickedItem.category)
            intent.putExtra("title", clickedItem.title)
            intent.putExtra("contents", clickedItem.contents)
            intent.putExtra("nickname", clickedItem.nickname)
            intent.putExtra("date", clickedItem.date)
            startActivity(intent)
        }

        return view
    }

}