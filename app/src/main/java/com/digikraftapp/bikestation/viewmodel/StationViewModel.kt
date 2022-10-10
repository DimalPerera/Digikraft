package com.digikraftapp.bikestation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digikraftapp.bikestation.model.Feature
import com.digikraftapp.bikestation.repository.StationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(private val repository: StationRepository) :
    ViewModel() {

    var bikedada: MutableLiveData<List<Feature>> = MutableLiveData()

    var isLoading = MutableLiveData<Boolean>()
    //var isNoData = MutableLiveData<Boolean>()


    /**
     * Initial station data loading
     */

    init {
        getBikeLiveData()
    }

    /**
    * MutableLiveData can observe when it is value changed.
    * Here isLoading use for updating UI element until JSON data is loading
    */
    private fun getBikeLiveData(): MutableLiveData<List<Feature>> {
        isLoading.value = true
        viewModelScope.launch {
            bikedada.value = repository.getJsonObject()?.features
            isLoading.value = false
        }
        return bikedada;
    }


    fun getData(): MutableLiveData<List<Feature>> {
        return bikedada
    }
}
