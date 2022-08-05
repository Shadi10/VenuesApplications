package com.example.findvenues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val venuesList: List<Result>) :
    RecyclerView.Adapter<VenuesViewHolder>() {



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):VenuesViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.data, viewGroup, false)

        return VenuesViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: VenuesViewHolder, position: Int) {

        viewHolder.setViews(venuesList[position])
    }

    override fun getItemCount() = venuesList.size

}
