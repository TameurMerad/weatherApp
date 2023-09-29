package com.example.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uiapptest.R
import com.squareup.picasso.Picasso

class myAdapter(val myList: List<Hour>):RecyclerView.Adapter<myAdapter.myAdapterViewHolder>() {
    inner class myAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myAdapterViewHolder {
        return myAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: myAdapterViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.timeRcId).text = myList[position].time.takeLast(5)
            findViewById<TextView>(R.id.rcDegreeId).text = myList[position].temp_c.toString()+"°"
            val image = findViewById<ImageView>(R.id.rcImgId)
            Picasso.get()
                .load("https:" + myList[position].condition.icon)
                .into(image)
        }
    }


}