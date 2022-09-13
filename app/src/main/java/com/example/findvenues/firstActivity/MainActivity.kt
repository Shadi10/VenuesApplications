package com.example.findvenues.firstActivity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import com.example.findvenues.R
import com.example.findvenues.databinding.ActivityMainBinding
import com.example.findvenues.firstActivity.fragments.ListFragment
import com.example.findvenues.firstActivity.fragments.MapFragment
import com.example.findvenues.secondActivity.SecondActivity
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.lifecycle.MutableLiveData
import com.example.findvenues.loginActivity.LoginActivity
import com.example.findvenues.firstActivity.fragments.CustomDialogFragment
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<VenuesViewModel>()
    private var doubleBackToExitPressedOnce = false
    private val mHandler: Handler = Handler()
    private val channelId = "channelId"
    private val channelName = "channelName"
    private val notificationId = 0
    private val mRunnable = Runnable {
        doubleBackToExitPressedOnce = false
    }
    private val data: MutableLiveData<List<Result>> = MutableLiveData(listOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val listFragment = ListFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, listFragment).commit()
        }
        setSupportActionBar(binding.toolbar)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                viewModel.venuesLiveData.value?.forEach { result ->
                    if (result.name.contains(query.toString())) {
                        data.value = data.value?.plus(result) ?: listOf(result)
                    }
                }
                viewModel.venuesLiveData.observe(this@MainActivity, {
                    listFragment.setUpRecyclerView(data.value!!)
                })
                data.value = listOf()

                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, listFragment).commit()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.venuesLiveData.value?.forEach { result ->
                    if (result.name.contains(newText.toString())) {

                        data.value = data.value?.plus(result) ?: listOf(result)

                        viewModel.venuesLiveData.observe(this@MainActivity, {
                            listFragment.setUpRecyclerView(data.value!!)
                        })
                    }
                }
                data.value = listOf()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, listFragment).commit()
                }
                return false
            }

        })
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            mHandler.postDelayed(mRunnable, 2000)
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()


    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)

        val intent = Intent(this, SplashScreen::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Notification")
            .setContentText("It's time to EXPLORE new Venues Around the Globe!")
            .setSmallIcon(R.drawable.venues_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .build()

        val notificationManager = from(this)
        notificationManager.notify(notificationId, notification)
    }



    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val mapFragment = MapFragment()
        val listFragment = ListFragment()
        val dialogFragment = CustomDialogFragment()

        when (item.itemId) {
            R.id.venuesList -> {

                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, listFragment).commit()
                    binding.toolbarTitle.text = "Venues List"
                }


            }
            R.id.venuesMap -> {

                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, mapFragment).commit()
                    binding.toolbarTitle.text = "Venues Map"
                }


            }
            R.id.logout -> {

                val preferences: SharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("remember", "")
                editor.apply()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)

            }
            R.id.savedVenues -> {

                val intent = Intent(applicationContext, SecondActivity::class.java)
                startActivity(intent)

            }
            R.id.venuesOption -> {
                dialogFragment.show(supportFragmentManager, "customDialog")
            }
        }
        return true
    }

}
