package com.sners.xmascardlist_v4.data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

//  This class is required in order to ...
//  You'll create a view model factory that allows you to define a custom constructor for a ViewModel
//  that gets called when you use ViewModelProviders.
//  https://en.wikipedia.org/wiki/Factory_(object-oriented_programming)
class ContactVMFactory(private val contactId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactVM::class.java)) {
            return  ContactVM(contactId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

