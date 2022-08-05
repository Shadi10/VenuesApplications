package com.example.findvenues

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VenuesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameTextView: TextView = view.findViewById(R.id.name)
    private val countryTextView: TextView = view.findViewById(R.id.country)
    private val addressTextView: TextView = view.findViewById(R.id.address)

    fun setViews(venue: Result){
        nameTextView.text = venue.name
        countryTextView.text = venue.location.country
        addressTextView.text = venue.location.address

    }
}
