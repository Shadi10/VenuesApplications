package com.example.findvenues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findvenues.databinding.ActivityMainBinding
import android.content.Intent





class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listFragment = ListFragment()
        val mapFragment = MapFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, listFragment).commit()
        }
        binding.btnList.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, listFragment).commit()
            }

        }
        binding.btnMap.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, mapFragment).commit()
            }

        }
        binding.secondActivityBtn.setOnClickListener{
            val i = Intent(applicationContext, SecondActivity::class.java)
            startActivity(i)

        }

    }
}