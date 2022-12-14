package com.example.findvenues.firstActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findvenues.R

class MyAdapter(private val venuesList: List<Result>, private val recyclerViewInterface: RecyclerViewInterface) :
    RecyclerView.Adapter<VenuesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VenuesViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_layout, viewGroup, false)
        return VenuesViewHolder(view,recyclerViewInterface)
    }


    override fun onBindViewHolder(viewHolder: VenuesViewHolder, position: Int) {

        viewHolder.setViews(venuesList[position])
    }

    override fun getItemCount() = venuesList.size

}
