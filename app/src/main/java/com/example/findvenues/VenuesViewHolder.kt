package com.example.findvenues

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class VenuesViewHolder(view: View,recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(view) {
    val nameTextView: TextView = view.findViewById(R.id.name)
     val countryTextView: TextView = view.findViewById(R.id.country)
     val addressTextView: TextView = view.findViewById(R.id.address)
    init {
       view.setOnClickListener{
           val position = adapterPosition
           if(position!= RecyclerView.NO_POSITION){
               recyclerViewInterface.onItemClick(position)
           }
        }
    }
    fun setViews(venue: Result){
        nameTextView.text = venue.name
        countryTextView.text = venue.location.country
        addressTextView.text = venue.location.address

    }
}
