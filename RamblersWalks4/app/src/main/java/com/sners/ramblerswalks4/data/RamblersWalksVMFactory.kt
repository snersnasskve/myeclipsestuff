package com.sners.ramblerswalks4.data
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RamblersWalksVMFactory(
    private val application: Application
) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupsViewModel::class.java))
        {
            return GroupsViewModel(application) as T
        }
        else   if (modelClass.isAssignableFrom(DistanceViewModel::class.java))
        {
            return DistanceViewModel() as T
        }
        else   if (modelClass.isAssignableFrom(DaysViewModel::class.java))
        {
            return DaysViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class (top level)")
    }
}


