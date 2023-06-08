package com.example.capstone_writers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.capstone_writers.DataModel.DataModel

class ListViewAdapter(val List : MutableList<DataModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(position: Int): Any {
        return List[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView
        if (convertView == null) {

            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.listview_item, parent, false)

        }

        val Username = convertView?.findViewById<TextView>(R.id.listviewUsername)
        val Useremail = convertView?.findViewById<TextView>(R.id.listviewUseremail)

        Username!!.text = List[position].reqUsername
        Useremail!!.text = List[position].reqUseremail

        return convertView!!
    }

}