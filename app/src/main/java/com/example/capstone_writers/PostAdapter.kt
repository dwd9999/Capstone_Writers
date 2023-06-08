package com.example.capstone_writers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.capstone_writers.DataModel.PostModel

class PostAdapter(val List : MutableList<PostModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(p0: Int): Any {
        return List[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView  = p1
        if (convertView == null) {

            convertView = LayoutInflater.from(p2?.context).inflate(R.layout.post_item, p2, false)

        }

        val image = convertView?.findViewById<ImageView>(R.id.ThumbImage)
        val title = convertView?.findViewById<TextView>(R.id.TitleText)
        val contents = convertView?.findViewById<TextView>(R.id.ContentsText)
        val nickname = convertView?.findViewById<TextView>(R.id.NicknameText)


        title!!.text = List[p0].title
        contents!!.text = List[p0].contents
        nickname!!.text = List[p0].nickname

        return convertView!!
    }
}