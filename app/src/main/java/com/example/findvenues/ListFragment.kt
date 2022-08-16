package com.example.findvenues

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findvenues.data.Venue
import com.example.findvenues.data.VenuesViewModel
import com.example.findvenues.databinding.FragmentListBinding


class ListFragment : Fragment(), RecyclerViewInterface {
    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<VenuesViewModel>()
    private lateinit var mVenueViewModel: VenuesViewModel
//    private var itemPos: Int = 0
//    var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.userInputBtn.setOnClickListener {
            val userInput = binding.inputOpenAt.text.toString()
            viewModel.getData(userInput)
            viewModel.venuesLiveData.observe(viewLifecycleOwner, { venueList ->
                setUpRecyclerView(venueList)
            })
        }
        val userInput = binding.inputOpenAt.text.toString()
        viewModel.getData(userInput)
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
        mVenueViewModel = ViewModelProvider(this)[VenuesViewModel::class.java]

        return binding.root
    }

    private fun setUpRecyclerView(venuesList: List<Result>) {
        binding.recyclerview.layoutManager = LinearLayoutManager(this.activity)
        binding.recyclerview.adapter = MyAdapter(venuesList, this)
    }

    private fun insertDataToDatabase(viewHolder: VenuesViewHolder) {        // Insert list into the database
        val name = viewHolder.nameTextView.text.toString()
        val country = viewHolder.countryTextView.text.toString()
        val address = viewHolder.addressTextView.text.toString()
        val venue = Venue(0, name, country, address)
        mVenueViewModel.addVenue(venue)

    }


    override fun onItemClick(position: Int) {   // switch to the map when clicking on an item
//        isClicked = true
//        itemPos = position
//        getPosition()
        Toast.makeText(view?.context, "Item $position is clicked", Toast.LENGTH_SHORT).show()
        val mapFragment = MapFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, mapFragment).commit()
        fragmentTransaction.addToBackStack(null)
    }

//    fun getPosition(): Int {
//        return itemPos
//    }


}

