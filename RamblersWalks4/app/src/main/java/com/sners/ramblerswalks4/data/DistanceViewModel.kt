package com.sners.ramblerswalks4.data

import androidx.lifecycle.ViewModel
import timber.log.Timber

class DistanceViewModel : ViewModel() {
    init {
        Timber.i("Distance View Model created from karen")

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Distance View Model cleared from karen")
    }
}