package com.sners.ramblerswalks4.data

import androidx.lifecycle.ViewModel
import timber.log.Timber

class RamblersViewModel : ViewModel() {
    init {
        Timber.i("View Model created from karen")

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("View Model cleared from karen")
    }
}