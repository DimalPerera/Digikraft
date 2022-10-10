package com.digikraftapp.bikestation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digikraftapp.bikestation.model.Feature
import com.digikraftapp.bikestation.model.Geometry
import com.google.android.gms.maps.model.LatLng

class StationDetailViewModel : ViewModel() {

    var station:  MutableLiveData<Feature> = MutableLiveData()
    var location:  MutableLiveData<LatLng> = MutableLiveData()


    /**
     * Set station data to ViewModel for future usage
     */

    fun setStation(station: Feature) {

        this.station.value = station
        getLocation(station.geometry)
    }

    /**
     * Convert Geometry JSON response to LatLng object
     */

    private fun getLocation(geometry: Geometry?) {
        val lat =  geometry?.coordinates?.get(0)
        val lng =  geometry?.coordinates?.get(1)

        if(lat != null && lng != null){
            this.location.value = LatLng(lat, lng)
        }else{
            this.location.value = null
        }

    }


}
