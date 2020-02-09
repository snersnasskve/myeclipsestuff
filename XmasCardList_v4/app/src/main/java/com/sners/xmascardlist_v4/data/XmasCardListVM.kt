package com.sners.xmascardlist_v4.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sners.xmascardlist_v4.data.database.ContactDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class XmasCardListVM (
    val database: ContactDao,
    application: Application) : AndroidViewModel(application){


    /// UPTO: Execise: Coroutines for Long-running
    //  First video - 4:30 minutes.
    //  Not tested at all - probly need to figure out how to add some data first

    //  Job is so that co-routines always have somewhere to return to.
    //  We need to use this to cancel any outstanding co-routines when this class is destroyed
    private var viewModelJob = Job()

    //  Scope determines what thread to run on, and sorts out that side of things
    //  Use Dispatchers.Main as we intend this data to go into the UI on Main
    private  val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var contacts :  MutableLiveData<List<Contact>>()

    init {
        this.readContacts()
    }


    override fun onCleared() {
        super.onCleared()
        //  Cancel all outstanding co-routines
        viewModelJob.cancel()
    }

    private fun readContacts()
    {
        //  Launch the co-routine, so that we do not block the main thread
        this.uiScope.launch {
             contacts = database.getAllContacts()
        }
    }

    private suspend fun readContactsFromDatabase(): List<Contact>>?
    {
        return withContext(Dispatchers.IO) {
            val dbContacts = database.getAllContacts()
            dbContacts
        }
    }
}