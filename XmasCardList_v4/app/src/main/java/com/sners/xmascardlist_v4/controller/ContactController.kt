package com.sners.xmascardlist_v4.controller

import com.sners.xmascardlist_v4.data.Contact

class ContactController {

    val contacts : MutableList<Contact> = ArrayList()
    val contactMap: MutableMap<String, Contact> = HashMap()


    init {
        val fred = Contact()
        fred.firstName = "Fred"
        fred.id = 0
        addContact(fred)
        val jack = Contact()
        jack.firstName = "Jack"
        jack.id = 1
        addContact(jack)
        val felicity = Contact()
        felicity.firstName = "Felicity"
        felicity.id = 2
        addContact(felicity)
    }

    fun addContact(contact: Contact)
    {
        contacts.add(contact)
        contactMap.put(contact.id.toString(), contact)
    }

    fun numContacts() : Int
    {
        return contacts.count()
    }
}