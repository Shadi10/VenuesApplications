package com.example.findvenues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.findvenues.data.VenuesViewModel
import com.example.findvenues.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment() : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private val viewModel by activityViewModels<VenuesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(binding.googleMap.id) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        allItemRender(googleMap)
    }

    private fun allItemRender(googleMap: GoogleMap) {       //rendering all markers on the map

        viewModel.venuesLiveData.observe(viewLifecycleOwner, { venuesList ->
            venuesList.forEach { venue ->
                val lat = venue.geocodes.main.latitude
                val long = venue.geocodes.main.longitude
                if (venue.fsq_id == viewModel.clickedVenue?.fsq_id) {
                    googleMap.addMarker(
                        MarkerOptions().position(LatLng(lat.toDouble(), long.toDouble()))
                            .title(venue.name)
                    )
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(lat.toDouble(), long.toDouble()),
                            15f
                        )
                    )

                } else {
                    googleMap.addMarker(
                        MarkerOptions().position(LatLng(lat.toDouble(), long.toDouble()))
                            .title(venue.name)
                    )
                }

            }

        })
    }
}

