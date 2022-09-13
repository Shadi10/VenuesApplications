package com.example.findvenues.firstActivity

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.findvenues.R

class VenuesViewHolder(private val view: View,private val recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(view) {
     val nameTextView: TextView = view.findViewById(R.id.name)
     val countryTextView: TextView = view.findViewById(R.id.country)
     val addressTextView: TextView = view.findViewById(R.id.address)
     val container : ConstraintLayout = view.findViewById(R.id.container)

    fun setViews(venue: Result){
            view.setOnClickListener{
                val position = adapterPosition
                if(position!= RecyclerView.NO_POSITION){
                    recyclerViewInterface.onItemClick(venue)
                }
            }
        container.animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_list)
        nameTextView.text = venue.name
        countryTextView.text = venue.location.country
        addressTextView.text = venue.location.address

    }
}
