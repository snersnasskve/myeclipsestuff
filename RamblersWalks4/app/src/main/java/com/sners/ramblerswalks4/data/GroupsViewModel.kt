package com.sners.ramblerswalks4.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sners.myfavouriterobot.utilities.FileHelper
import com.sners.ramblerswalks4.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import timber.log.Timber

class GroupsViewModel(app: Application ) : AndroidViewModel(app) {

    var app = app
    private val GroupListType = Types.newParameterizedType(
        List::class.java, Group::class.java
    )



    //  Job is so that co-routines always have somewhere to return to.
    //  We need to use this to cancel any outstanding co-routines when this class is destroyed
    private var viewModelJob = Job()

    //  Scope determines what thread to run on, and sorts out that side of things
    //  Use Dispatchers.Main as we intend this data to go into the UI on Main
    private  val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private  var groupData: List<Group> = emptyList()


    val _areaNames = MutableLiveData<List<String>> ()
    val areaNames : LiveData<List<String>>
        get() = _areaNames

    //--------------------------------------------------------------------
    init {
        Timber.i("Groups View Model created from karen")
        _areaNames.value = listOf("Loading...")
        this.parseGroupsData()
    }

    //  Deal with the areas
    //--------------------------------------------------------------------
    private fun parseGroupsData()
    {
        //  Launch the co-routine, so that we do not block the main thread
        this.uiScope.launch {
           val gData = getGroupsFromJson()
            if (gData != null)
            {
                groupData = gData
                val  areas : List<String> = groupData.filter {it.scope == "A" }.map { it.name }
                _areaNames.value = areas

                //  If we have a default area, we should now go ahead and populate the groupNames
            }
        }

    }

    //--------------------------------------------------------------------
    private suspend fun getGroupsFromJson() : List<Group>?
    {
        return withContext(Dispatchers.IO) {
            val groupsText = FileHelper.getTextFromResources(app, R.raw.areas_light )
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()
            val adapter: JsonAdapter<List<Group>> =
                moshi.adapter(GroupListType)
            //  So you cannot have this.groupData - scope????
            val readGroupData = adapter.fromJson(groupsText)
            readGroupData
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Groups View Model cleared from karen")
        //  Cancel all outstanding co-routines
        viewModelJob.cancel()
    }
}