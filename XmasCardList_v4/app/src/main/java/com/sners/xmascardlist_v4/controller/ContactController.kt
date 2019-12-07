package com.sners.xmascardlist_v4.controller

import com.sners.xmascardlist_v4.data.Contact

class ContactController {

    val contacts : MutableList<Contact> = ArrayList()
    val contactMap: MutableMap<String, Contact> = HashMap()


    init {
        val fred = Contact(0)
        //fred.firstName.value = "Fred"
        addContact(fred)
        val jack = Contact(1)
        //.firstName.value = "Jack"
        jack.contactId = 1
        addContact(jack)
        val felicity = Contact(2)
        //felicity.firstName.value = "Felicity"
        felicity.contactId = 2
        addContact(felicity)
    }

    fun addContact(contact: Contact)
    {
        contacts.add(contact)
        contactMap.put(contact.contactId.toString(), contact)
    }

    fun numContacts() : Int
    {
        return contacts.count()
    }
}