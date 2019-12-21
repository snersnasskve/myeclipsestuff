package com.sners.ramblerswalks4.data

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class DistanceViewModel : ViewModel(), LifecycleObserver {

    private val _minDistance = MutableLiveData<Int> ()
    val minDistance : LiveData<Int>
        get() = _minDistance

    private val _maxDistance = MutableLiveData<Int> ()
    val maxDistance : LiveData<Int>
        get() = _maxDistance

    init {
        Timber.i("Distance View Model created from karen")
        _minDistance.value = 3
        _maxDistance.value = 5

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Distance View Model cleared from karen")
    }

    fun sliderChanged(name: String, value: Int) {
        if (name == "min")
        {
            _minDistance.value = value
        }
        else    if (name == "max")
        {
            _maxDistance.value = value
        }
    }
}