package com.example.uiapptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.Forecastday
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class forcastAdapter(val forcastList: List<Forecastday>):RecyclerView.Adapter<forcastAdapter.forcastViewHolder>() {
    inner class forcastViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): forcastViewHolder {
        return forcastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_sec_item,parent,false))
    }

    override fun getItemCount(): Int {
        return forcastList.size
    }

    override fun onBindViewHolder(holder: forcastViewHolder, position: Int) {
        holder.itemView.apply {
            val day = findViewById<TextView>(R.id.dayID)
            val image = findViewById<ImageView>(R.id.imageView)
            val condition = findViewById<TextView>(R.id.conditionID)
            val maxAndmin = findViewById<TextView>(R.id.textView)
            val forcast = forcastList[position]


            val dateStr = forcast.date
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(dateStr, formatter)
            val dayName = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())



            Picasso.get()
                .load("https:"+forcast.day.condition.icon)
                .into(image)

            day.text = dayName
            condition.text = forcast.day.condition.text
            maxAndmin.text = "${forcast.day.maxtemp_c}°  ${forcast.day.mintemp_c}°"

        }
    }

}