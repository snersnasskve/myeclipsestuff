package com.sners.ramblerswalks4.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.sners.ramblerswalks4.data.SearchData
import timber.log.Timber

class SearchManager(lifecycle: Lifecycle)  : LifecycleObserver{
    val searchData = SearchData()

    init {
        // Add this as a lifecycle Observer, which allows for the class to react to changes in this
        // activity's lifecycle state
        lifecycle.addObserver(this)
        Timber.i("we have a controller called from karen")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun dummyMethod()
    {
        //  This gets called when you navigate away from the activity, not the app - figure it out before go live
        Timber.i("Controller got the ON PAUSE call from karen")
    }

}