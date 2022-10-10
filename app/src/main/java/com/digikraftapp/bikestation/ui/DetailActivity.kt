package com.digikraftapp.bikestation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.digikraftapp.bikestation.R
import com.digikraftapp.bikestation.databinding.ActivityDetailBinding
import com.digikraftapp.bikestation.model.Feature
import com.digikraftapp.bikestation.model.Geometry
import com.digikraftapp.bikestation.util.Const
import com.digikraftapp.bikestation.util.MapHelper
import com.digikraftapp.bikestation.viewmodel.StationDetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    //View binding
    private lateinit var binding: ActivityDetailBinding

    //View model
    private val viewModel: StationDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val station: Feature? = intent.getParcelableExtra(Const.STATION)


        if (station != null) {
            viewModel.setStation(station)
            setUpGoogleMapData()
            setStationData()
        }else{
            showErrorMessage("Station data not available. Please check again")
        }

    }

    //Initialize google map fragment
    private fun setUpGoogleMapData(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        //Observe station data changes
        viewModel.station.observe(this) {

            val location = getLocation(it?.geometry)

            if(location != null){
                googleMap.addMarker(
                    MarkerOptions()
                        .icon(MapHelper().getBitmapDescriptorFromVector(applicationContext, R.drawable.ic_bike))
                        .position(location)
                        .title("Station Location")
                )
                val cameraPosition = CameraPosition.Builder()
                    .target(location)
                    .zoom(5f)
                    .bearing(90f)
                    .tilt(30f)
                    .build()

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }else{

                showErrorMessage("Location data not available.");
            }
        }

    }

    private fun setStationData(){
        viewModel.station.observe(this) {
            binding.tvDetailStationName.text = it?.properties?.label
            binding.tvFreeBikeCount.text = it?.properties?.freeRacks
            binding.tvAllBikeCount.text = it?.properties?.bikeRacks
        }
    }

    private fun showErrorMessage( message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)

        builder.setNegativeButton("OK") { dialog, which ->
        }

        builder.show()
    }

    private fun getLocation(geometry: Geometry?): LatLng? {
        val lat =  geometry?.coordinates?.get(0)
        val lng =  geometry?.coordinates?.get(1)

        return if(lat != null && lng != null){
            LatLng(lat, lng)
        }else{
            null;
        }

    }



}
