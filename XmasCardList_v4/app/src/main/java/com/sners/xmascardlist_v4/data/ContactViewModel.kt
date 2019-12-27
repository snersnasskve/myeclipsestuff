package com.sners.xmascardlist_v4.data

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ContactViewModel (contactId : Int): ViewModel(), LifecycleObserver {

    var contactId : Int = 0

    private val _firstName = MutableLiveData<String> ()
    val firstName : LiveData<String>
    get() = _firstName


    init {
        this.contactId = contactId
        val dummyContacts = arrayOf("Fred", "Jack", "Felicity", "Andrilla")
        _firstName.value = dummyContacts[contactId % (dummyContacts.count())]
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Days View Model destroyed from karen")
    }
}