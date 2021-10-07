package com.example.weaterapp.ui.fragments.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.weaterapp.R
import com.example.weaterapp.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import java.util.*

class MapsFragment : Fragment(){

    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_json))
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
        googleMap.setOnMapClickListener { latLng ->
            getCity(latLng)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_maps, container, false)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fetchLocation()

        return view
    }


    private fun fetchLocation() {

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
            return
        } else {
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    val mapFragment =
                        childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(callback)
                }
            }
        }

    }

    private fun getCity(latLng: LatLng) {
        val geocode = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocode.getFromLocation(latLng.latitude, latLng.longitude,1)
        if (addresses.size > 0 ) {
            if (addresses[0].locality != null){
                Log.e(Constants.TAG, addresses[0].locality.toString())
            } else {
                Toast.makeText(requireContext(), "Tap on a city!", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1000 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

}