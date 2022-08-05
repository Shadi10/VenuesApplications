package com.example.findvenues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findvenues.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<VenuesViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getData()
        viewModel.venuesLiveData.observe(this, { venuesList ->
            setUpRecyclerView(venuesList)
        })
    }

    fun setUpRecyclerView(venuesList: List<Result>){
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter  = MyAdapter(venuesList)
    }

}
