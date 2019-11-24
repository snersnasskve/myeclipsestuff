package com.sners.ramblerswalks4.data

import androidx.lifecycle.ViewModel
import timber.log.Timber

class DaysViewModel : ViewModel() {

    var monday : Boolean = false
    var tuesday : Boolean = false
    var wednesday : Boolean = false
    var thursday : Boolean = false
    var friday : Boolean = false
    var saturday : Boolean = false
    var sunday : Boolean = false
    var weekdays : Boolean = false
    var weekend : Boolean = false
    var everyday : Boolean = false

    init {
        Timber.i("Days View Model created from karen")

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Days View Model destroyed from karen")
    }
}