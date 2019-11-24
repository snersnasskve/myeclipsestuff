package com.sners.ramblerswalks4.data

import androidx.lifecycle.ViewModel
import timber.log.Timber

class GroupsViewModel : ViewModel() {
    init {
        Timber.i("Groups View Model created from karen")

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Groups View Model cleared from karen")
    }
}