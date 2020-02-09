package com.sners.xmascardlist_v4.data

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sners.xmascardlist_v4.data.database.Contact
import com.sners.xmascardlist_v4.data.database.ContactDao
import kotlinx.coroutines.*
import timber.log.Timber

class ContactVM (val contactId : Long,
                 val database: ContactDao ): ViewModel(), LifecycleObserver {

    private val _firstName = MutableLiveData<String> ()
    val firstName : LiveData<String>
    get() = _firstName

    private var viewModelJob = Job()
    private val uisScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val contact = MutableLiveData<Contact?>()


    init {
//        val dummyContacts = arrayOf("Fred", "Jack", "Felicity", "Andrilla")
//        _firstName.value = dummyContacts[contactId % (dummyContacts.count())]
        readContact()
    }

    private fun readContact() {
        uisScope.launch {
            contact.value = getContactFromDatabase()
        }
    }

    private suspend fun getContactFromDatabase(): Contact {
return withContext(Dispatchers.IO) {
    var tempContact = database.getContact(contactId)
    tempContact
}
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Days View Model destroyed from karen")
        viewModelJob.cancel()
    }
}