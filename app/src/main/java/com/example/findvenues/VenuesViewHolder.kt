package com.example.findvenues

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class VenuesViewHolder(private val view: View,private val recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(view) {
     val nameTextView: TextView = view.findViewById(R.id.name)
     val countryTextView: TextView = view.findViewById(R.id.country)
     val addressTextView: TextView = view.findViewById(R.id.address)



    fun setViews(venue: Result){
            view.setOnClickListener{
                val position = adapterPosition
                if(position!= RecyclerView.NO_POSITION){
                    recyclerViewInterface.onItemClick(venue)
                }
            }
        nameTextView.text = venue.name
        countryTextView.text = venue.location.country
        addressTextView.text = venue.location.address

    }
}
