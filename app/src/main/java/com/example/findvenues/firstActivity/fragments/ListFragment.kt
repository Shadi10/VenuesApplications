package com.example.findvenues.firstActivity.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findvenues.R
import com.example.findvenues.data.venue.Venue
import com.example.findvenues.databinding.FragmentListBinding
import com.example.findvenues.firstActivity.*


class ListFragment : Fragment(), RecyclerViewInterface {
    private lateinit var binding: FragmentListBinding
    private val viewModel by activityViewModels<VenuesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel.venuesLiveData.observe(viewLifecycleOwner, { venueList ->
            setUpRecyclerView(venueList)
        })

        val swipeLeftOrRight = object : SwipeLeftOrRight() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val posOriginal =
                    binding.recyclerview.adapter?.notifyItemChanged(position)   // when dragged, it returns back to the original position
                binding.recyclerview.adapter?.notifyItemChanged(position, posOriginal)

                insertDataToDatabase(viewHolder as VenuesViewHolder)
                Toast.makeText(context, "Saved to database", Toast.LENGTH_LONG).show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeLeftOrRight)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)


        return binding.root
    }

    fun setUpRecyclerView(venuesList: List<Result>) {
        binding.recyclerview.layoutManager = LinearLayoutManager(this.activity)
        binding.recyclerview.adapter = MyAdapter(venuesList, this)
    }

    private fun insertDataToDatabase(viewHolder: VenuesViewHolder) {
        val name = viewHolder.nameTextView.text.toString()
        val country = viewHolder.countryTextView.text.toString()
        val address = viewHolder.addressTextView.text.toString()

        val pref: SharedPreferences? =
            this.activity?.getSharedPreferences("myPref", AppCompatActivity.MODE_PRIVATE)
        val id = pref?.getInt("userId", 0)
        val venue = id?.let { Venue(0, name, country, address, it) }
        if (venue != null) {
            viewModel.addVenue(venue)
        }

    }


    override fun onItemClick(venue: Result) {

        viewModel.clickedVenue = venue
        val mapFragment = MapFragment()

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, mapFragment).commit()
        fragmentTransaction.addToBackStack(null)

    }
}

