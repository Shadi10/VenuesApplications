package com.example.findvenues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findvenues.data.Venue
import com.example.findvenues.data.VenueDatabase
import com.example.findvenues.data.VenuesViewModel
import com.example.findvenues.databinding.ActivitySecondBinding
import com.example.findvenues.databinding.FragmentListBinding
import kotlinx.coroutines.launch


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val dbVenuesViewModel by viewModels<DbVenuesViewModel>()

    //    private val noteDatabase by lazy { VenueDatabase.getDatabase(this).venueDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbVenuesViewModel.getData()
        dbVenuesViewModel.venuesLiveData.observe(this, { venueList ->
            setUpRecyclerView(venueList)
        })
        val listFragment =ListFragment()
        binding.backToVenuesBtn.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(binding.root.id, listFragment).commit()
            }
        }
    }

    private fun setUpRecyclerView(venuesList: List<Venue>) {
        binding.secondActivityRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.secondActivityRecyclerview.adapter = SecondActivityAdapter(venuesList)
    }
}
//    private fun observeNotes() {
//        lifecycleScope.launch {
//            noteDatabase.getVenues().collect { notesList ->
//                if (notesList.isNotEmpty()) {
//                    adapter.submitList(notesList)
//                }
//            }


