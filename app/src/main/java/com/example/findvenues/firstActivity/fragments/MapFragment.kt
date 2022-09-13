package com.example.findvenues.firstActivity.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.findvenues.R
import com.example.findvenues.databinding.FragmentMapBinding
import com.example.findvenues.firstActivity.VenuesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapFragment() : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private lateinit var binding: FragmentMapBinding
    private val viewModel by activityViewModels<VenuesViewModel>()
    private var gMap: GoogleMap? = null
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
         googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(30.0, 20.0))
                .draggable(true)
                .icon(activity?.let {
                    bitmapFromVector(
                        it.applicationContext,
                        R.drawable.ic_baseline_maps_ugc_24
                    )
                })
        )
        googleMap.setOnMarkerDragListener(this)
        oneItemRender(googleMap)
        googleMap.uiSettings.isZoomControlsEnabled = true;
    }

    private fun allItemRender(googleMap: GoogleMap) {       //rendering all markers on the map

        viewModel.venuesLiveData.observe(viewLifecycleOwner, { venuesList ->
            venuesList.forEach { venue ->
                val lat = venue.geocodes.main.latitude
                val long = venue.geocodes.main.longitude

                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            lat.toDouble(),
                            long.toDouble()
                        )
                    ).title(venue.name)
                )
                    .toString()

            }
        })
    }

    private fun oneItemRender(googleMap: GoogleMap) {
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
                            20f
                        )
                    )
                } else {
                    googleMap.addMarker(
                        MarkerOptions().position(LatLng(lat.toDouble(), long.toDouble()))
                            .title(venue.name)
                    )
                }
            }
            viewModel.clickedVenue = null
        })
    }

    override fun onMarkerDrag(p0: Marker) {
        Toast.makeText(
            context,
            "",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
        Toast.makeText(
            context,
            "",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onMarkerDragEnd(marker: Marker) {
        viewModel.marker = marker
        val ll = viewModel.getLl()
        viewModel.getData(ll)
        gMap?.let { allItemRender(it) }
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {

        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}

