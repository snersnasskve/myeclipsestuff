package com.sners.xmascardlist_v4.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

//  Dao is how you access a database. This auto-generates the sqlite qeries in most cases
//  NB this is an interface not a class
@Dao
interface ContactDao {

    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM xmas_card_contact_table ORDER BY contactId DESC LIMIT 1")
    fun getTonight(): Contact?

//
//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "ORDER BY first_name ASC")
//    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table ")
    fun getAllContacts(): LiveData<List<Contact>>


    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE contactId = :key")
    fun getContact(key: Long): Contact

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE favourite = true " +
            "ORDER BY first_name ASC")
    fun getFavourites() : LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE country = :inCountry " +
            "ORDER BY first_name ASC")
    fun getForCountry(inCountry: String) : LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE xmas_card_received LIKE :reqYear " +
            "ORDER BY first_name ASC")
    fun getForXmasReceived(reqYear: String) : LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE xmas_card_sent = :sent " +
            "ORDER BY first_name ASC"    )
    fun getForXmasSent(sent: Boolean) : LiveData<List<Contact>>


    @Query("SELECT country FROM xmas_card_contact_table " +
            "GROUP BY country " +
            "ORDER BY country ASC")
    fun getCountries() : LiveData<List<String>>


    @Query("DELETE FROM xmas_card_contact_table")
    fun clearAll()

}