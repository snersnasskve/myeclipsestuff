package com.sners.ramblerswalks4.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class DistanceViewModel : ViewModel() {

    private val _minDistance = MutableLiveData<Int> ()
    val minDistance : LiveData<Int>
        get() = _minDistance

    private val _maxDistance = MutableLiveData<Int> ()
    val maxDistance : LiveData<Int>
        get() = _maxDistance

    init {
        Timber.i("Distance View Model created from karen")

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Distance View Model cleared from karen")
    }
}