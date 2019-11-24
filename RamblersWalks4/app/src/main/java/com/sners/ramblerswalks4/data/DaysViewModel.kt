package com.sners.ramblerswalks4.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class DaysViewModel : ViewModel() {

    var monday = MutableLiveData<Boolean> ()
    var tuesday = MutableLiveData<Boolean> ()
    var wednesday = MutableLiveData<Boolean> ()
    var thursday = MutableLiveData<Boolean> ()
    var friday = MutableLiveData<Boolean> ()
    var saturday = MutableLiveData<Boolean> ()
    var sunday = MutableLiveData<Boolean> ()
    var weekdays = MutableLiveData<Boolean> ()
    var weekend = MutableLiveData<Boolean> ()
    var everyday = MutableLiveData<Boolean> ()

    init {
        Timber.i("Days View Model created from karen")
        monday.value = false
        tuesday.value = false
        wednesday.value = false
        thursday.value = false
        friday.value = false
        saturday.value = false
        sunday.value = false
        weekdays.value = false
        weekend.value = false
        everyday.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Days View Model destroyed from karen")
    }

    fun tickBoxChanged(name: String, checked: Boolean)
    {
        Timber.i("Received a tick event for $name of value $checked from karen")
        when(name) {
            "Monday" -> this.monday.value = checked
            "Tuesday" -> this.tuesday.value = checked
            "Wednesday" -> this.wednesday.value = checked
            "Thursday" -> this.thursday.value = checked
            "Friday" -> this.friday.value = checked
            "Saturday" -> this.saturday.value = checked
            "Sunday" -> this.sunday.value = checked
            "Weekdays" -> this.weekdaysChanged(checked)
            "Weekends" -> this.weekendsChanged(checked)
            "Every Day" -> {
                this.weekdaysChanged(checked)
                this.weekendsChanged(checked)
                this.everyday.value = checked
            }
            else -> Timber.i("Unknown tick event for $name from karen")
        }
        if (!checked){
            this.everyday.value = checked
            this.everyday.value = checked
        }
    }

    fun weekdaysChanged(checked: Boolean)
    {
        this.weekdays.value = checked
        this.monday.value = checked
        this.tuesday.value = checked
        this.wednesday.value = checked
        this.thursday.value = checked
        this.friday.value = checked
    }

    fun weekendsChanged(checked: Boolean)
    {
        this.weekend.value = checked
        this.saturday.value = checked
        this.sunday.value = checked
    }
}