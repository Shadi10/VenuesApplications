package com.example.findvenues.secondActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findvenues.R
import com.example.findvenues.data.venue.Venue
import com.example.findvenues.databinding.ActivitySecondBinding
import com.example.findvenues.firstActivity.MainActivity
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val dbVenuesViewModel by viewModels<DbVenuesViewModel>()
    private val csvVenuesFile = "VenuesRoomDatabase.csv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val pref: SharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE)
        val id = pref.getInt("userId", 0);
            dbVenuesViewModel.getData(id)

        dbVenuesViewModel.venuesLiveData.observe(this, { venueList ->
            setUpRecyclerView(venueList)
        })
        binding.backToVenuesBtn.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun setUpRecyclerView(venuesList: List<Venue>) {
        binding.secondActivityRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.secondActivityRecyclerview.adapter = SecondActivityAdapter(venuesList)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second_activity, menu)

        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.exportToCsv) {
            exportDbToCsvFile()

        }
        return true
    }

    private fun exportDbToCsvFile() {

        val csvFile = generateFile(this, csvVenuesFile)
        if (csvFile != null) {
            exportVenuesToCSVFile(csvFile)
            Toast.makeText(this, "CSV file generated!", Toast.LENGTH_SHORT).show()
            intent = goToFileIntent(this,csvFile)
        } else {
            Toast.makeText(this, "CSV file not found", Toast.LENGTH_LONG).show()
        }

    }

    private fun exportVenuesToCSVFile(csvFile: File) {
        csvWriter().open(csvFile) {
            writeRow(listOf("id", "name", "country", "address"))
            dbVenuesViewModel.venuesLiveData.value?.forEach { venueList ->
                writeRow(
                    listOf(
                        venueList.id,
                        venueList.name,
                        venueList.country,
                        venueList.address
                    )
                )
            }

        }
    }
}



