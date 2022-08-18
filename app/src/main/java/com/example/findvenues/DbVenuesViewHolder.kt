package com.example.findvenues

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.findvenues.data.Venue

class DbVenuesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTextView: TextView = view.findViewById(R.id.name)
    private val countryTextView: TextView = view.findViewById(R.id.country)
    private val addressTextView: TextView = view.findViewById(R.id.address)


    fun setViews(venue: Venue){

        nameTextView.text = venue.name
        countryTextView.text = venue.country
        addressTextView.text = venue.address

    }
}
