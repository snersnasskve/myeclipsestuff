package com.sners.ramblerswalks4.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class RamblersViewModel : ViewModel() {
    private val _daysDescription = MutableLiveData<String> ()
    val daysDescription : LiveData<String>
        get() = _daysDescription

    private val _distanceDescription = MutableLiveData<String> ()
    val distanceDescription : LiveData<String>
        get() = _distanceDescription


    fun setDaysDescription(desc: String)
    {
        this._daysDescription.value = desc
    }

    fun setDistanceDescription(desc: String)
    {
        this._distanceDescription.value = desc
    }
}