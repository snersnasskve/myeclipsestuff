package com.sners.ramblerswalks4.data

import android.content.res.Resources
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sners.ramblerswalks4.DaysFragment
import com.sners.ramblerswalks4.R
import com.sners.ramblerswalks4.controller.DaysController
import kotlinx.android.synthetic.main.fragment_days.view.*
import timber.log.Timber

typealias DayName = DaysController.Companion.DayName

class DaysViewModel : ViewModel(), LifecycleObserver {


    //  Mutable live data is editable and should only be accessible within the class
    //  Expose a non-mutable LiveData value to external

    private val _monday = MutableLiveData<Boolean>()
    val monday: LiveData<Boolean>
        get() = _monday

    private val _tuesday = MutableLiveData<Boolean>()
    val tuesday: LiveData<Boolean>
        get() = _tuesday

    private val _wednesday = MutableLiveData<Boolean>()
    val wednesday: LiveData<Boolean>
        get() = _wednesday

    private val _thursday = MutableLiveData<Boolean>()
    val thursday: LiveData<Boolean>
        get() = _thursday

    private val _friday = MutableLiveData<Boolean>()
    val friday: LiveData<Boolean>
        get() = _friday

    private val _saturday = MutableLiveData<Boolean>()
    val saturday: LiveData<Boolean>
        get() = _saturday

    private val _sunday = MutableLiveData<Boolean>()
    val sunday: LiveData<Boolean>
        get() = _sunday

    private val _weekdays = MutableLiveData<Boolean>()
    val weekdays: LiveData<Boolean>
        get() = _weekdays

    private val _weekend = MutableLiveData<Boolean>()
    val weekend: LiveData<Boolean>
        get() = _weekend

    private val _everyday = MutableLiveData<Boolean>()
    val everyday: LiveData<Boolean>
        get() = _everyday

//    val daysDescription : String
//        get() {
//            var desc = "None"
//            val selectedDays = ArrayList<String>()
//            if(monday.value!!)
//            {
//                selectedDays.add(DayName.MONDAY.day)
//            }
//            if(tuesday.value!!)
//            {
//                selectedDays.add(DayName.TUESDAY.day)
//            }
//            if(wednesday.value!!)
//            {
//                selectedDays.add(DayName.WEDNESDAY.day)
//            }
//            if(thursday.value!!)
//            {
//                selectedDays.add(DayName.THURSDAY.day)
//            }
//            if(friday.value!!)
//            {
//                selectedDays.add(DayName.FRIDAY.day)
//            }
//            if(saturday.value!!)
//            {
//                selectedDays.add(DayName.SATURDAY.day)
//            }
//            if(sunday.value!!)
//            {
//                selectedDays.add(DayName.SUNDAY.day)
//            }
//            if (selectedDays.count() > 0) {
//                desc = selectedDays.joinToString(separator = ", ")
//            }
//            return desc
//        }


    init {
        Timber.i("Days View Model created from karen")
        _monday.value = false
        _tuesday.value = false
        _wednesday.value = false
        _thursday.value = false
        _friday.value = false
        _saturday.value = false
        _sunday.value = false
        _weekdays.value = false
        _weekend.value = false
        _everyday.value = false
    }

    override fun onCleared() {
        super.onCleared()
        //  Save data -- See App Archictecture with Kotlin (lynda) 1-Handle events with LifecycleObserver
        //  OK cleared is getting called at the start oops
        Timber.i("Days View Model destroyed from karen")
    }

    fun tickBoxChanged(name: String, checked: Boolean) {
        Timber.i("Received a tick event for $name of value $checked from karen")
        when (name) {
            "Monday" -> _monday.value = checked
            "Tuesday" -> _tuesday.value = checked
            "Wednesday" -> _wednesday.value = checked
            "Thursday" -> _thursday.value = checked
            "Friday" -> _friday.value = checked
            "Saturday" -> _saturday.value = checked
            "Sunday" -> _sunday.value = checked
            "Weekdays" -> weekdaysChanged(checked)
            "Weekends" -> weekendsChanged(checked)
            "Every Day" -> {
                this.weekdaysChanged(checked)
                this.weekendsChanged(checked)
                _everyday.value = checked
            }
            else -> Timber.i("Unknown tick event for $name from karen")
        }

    }

    private fun weekdaysChanged(checked: Boolean) {
        _weekdays.value = checked
        _monday.value = checked
        _tuesday.value = checked
        _wednesday.value = checked
        _thursday.value = checked
        _friday.value = checked
    }

    fun weekendsChanged(checked: Boolean) {
        _weekend.value = checked
        _saturday.value = checked
        _sunday.value = checked
    }

    fun getDaysArray(): Array<Boolean> {
        var daysTicked = arrayOf(
            monday.value!!, tuesday.value!!, wednesday.value!!,
            thursday.value!!, friday.value!!, saturday.value!!, sunday.value!!,
            weekdays.value!!, weekend.value!!, everyday.value!!
        )
        return daysTicked
    }

    fun setDaysFromArray(daysArray: Array<Boolean>) {
        _monday.value = daysArray[0]
        _tuesday.value = daysArray[1]
        _wednesday.value = daysArray[2]
        _thursday.value = daysArray[3]
        _friday.value = daysArray[4]
        _saturday.value = daysArray[5]
        _sunday.value = daysArray[6]
        _weekdays.value = daysArray[7]
        _weekend.value = daysArray[8]
        _everyday.value = daysArray[9]
    }

}