package com.example.findvenues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.findvenues.data.VenuesViewModel
import com.example.findvenues.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.text.FieldPosition


class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private val viewModel by viewModels<VenuesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        viewModel.getData()
        val mapFragment =
            childFragmentManager.findFragmentById(binding.googleMap.id) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
//        val listFragment = ListFragment()
//        if (listFragment.isClicked) {
//            singleItemRender(googleMap)
//        } else {
            allItemRender(googleMap)
//        }
    }  private fun allItemRender(googleMap: GoogleMap) {       //rendering all markers on the map
        viewModel.venuesLiveData.observe(viewLifecycleOwner, { venuesList ->
            for (i in venuesList.indices step 1) {
                val lat = venuesList[i].geocodes.main.latitude
                val long = venuesList[i].geocodes.main.longitude

                googleMap.addMarker(
                    MarkerOptions().position(LatLng(lat.toDouble(), long.toDouble()))
                        .title(venuesList[i].name)

                )
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(lat.toDouble(), long.toDouble()),
                        10f
                    )
                )
            }
        })
    }

//    private fun singleItemRender(googleMap: GoogleMap) {
//        val listFragment = ListFragment()
////        var position = listFragment.getPosition()
//
//        viewModel.venuesLiveData.observe(viewLifecycleOwner, { venuesList ->
//            val lat = venuesList[position].geocodes.main.latitude
//            val long = venuesList[position].geocodes.main.longitude
//            googleMap.addMarker(
//                MarkerOptions().position(LatLng(lat.toDouble(), long.toDouble()))
//                    .title(venuesList[position].name)
//
//            )
//            googleMap.moveCamera(
//                CameraUpdateFactory.newLatLngZoom(
//                    LatLng(lat.toDouble(), long.toDouble()),
//                    10f
//                )
//            )
//        })
//    }


}

