package com.example.findvenues.secondActivity

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.findvenues.R
import com.example.findvenues.data.venue.Venue

class DbVenuesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val nameTextView: TextView = view.findViewById(R.id.name)
    private val countryTextView: TextView = view.findViewById(R.id.country)
    private val addressTextView: TextView = view.findViewById(R.id.address)
    val container : ConstraintLayout = view.findViewById(R.id.container)

    fun setViews(venue: Venue){
        container.animation = AnimationUtils.loadAnimation(view.context, R.anim.top_animation)
        nameTextView.text = venue.name
        countryTextView.text = venue.country
        addressTextView.text = venue.address

    }
}
