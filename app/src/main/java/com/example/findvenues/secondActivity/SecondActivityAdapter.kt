package com.example.findvenues.secondActivity



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findvenues.R
import com.example.findvenues.data.venue.Venue

class SecondActivityAdapter(private val venueData: List<Venue>):RecyclerView.Adapter<DbVenuesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DbVenuesViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_layout, viewGroup, false)
        return DbVenuesViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: DbVenuesViewHolder, position: Int) {
        viewHolder.setViews(venueData[position])
    }

    override fun getItemCount() = venueData.size



}
