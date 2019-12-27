package com.sners.xmascardlist_v4.controller

import com.sners.xmascardlist_v4.data.ContactViewModel

class ContactController {

    val contacts : MutableList<ContactViewModel> = ArrayList()
    val contactMap: MutableMap<String, ContactViewModel> = HashMap()


    init {
        val fred = ContactViewModel(0)
        //fred.firstName.value = "Fred"
        addContact(fred)
        val jack = ContactViewModel(1)
        //.firstName.value = "Jack"
        jack.contactId = 1
        addContact(jack)
        val felicity = ContactViewModel(2)
        //felicity.firstName.value = "Felicity"
        felicity.contactId = 2
        addContact(felicity)
    }

    fun addContact(contact: ContactViewModel)
    {
        contacts.add(contact)
        contactMap.put(contact.contactId.toString(), contact)
    }

    fun numContacts() : Int
    {
        return contacts.count()
    }
}