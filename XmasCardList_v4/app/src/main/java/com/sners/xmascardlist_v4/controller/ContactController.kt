package com.sners.xmascardlist_v4.controller

import com.sners.xmascardlist_v4.data.ContactVM

class ContactController {

    val contacts : MutableList<ContactVM> = ArrayList()
    val contactMap: MutableMap<String, ContactVM> = HashMap()


    init {
        val fred = ContactVM(0)
        //fred.firstName.value = "Fred"
        addContact(fred)
        val jack = ContactVM(1)
        //.firstName.value = "Jack"
        jack.contactId = 1
        addContact(jack)
        val felicity = ContactVM(2)
        //felicity.firstName.value = "Felicity"
        felicity.contactId = 2
        addContact(felicity)
    }

    fun addContact(contact: ContactVM)
    {
        contacts.add(contact)
        contactMap.put(contact.contactId.toString(), contact)
    }

    fun numContacts() : Int
    {
        return contacts.count()
    }
}