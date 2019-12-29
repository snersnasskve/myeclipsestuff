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

    @Query("SELECT * FROM xmas_card_contact_table " +
            "ORDER BY firstName ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE contactId = :key")
    fun getContact(key: Long): Contact

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE favourite = true " +
            "ORDER BY firstName ASC")
    fun getFavourites() : LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE country = ':country' " +
            "ORDER BY firstName ASC")
    fun getForCountry(country: String) : LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE country = ':year' " +
            "ORDER BY firstName ASC")
    fun getForXmasReceived(year: String) : LiveData<List<Contact>>

    @Query("SELECT * FROM xmas_card_contact_table " +
            "WHERE xmas_card_sent = :sent " +
            "ORDER BY firstName ASC"    )
    fun getForXmasSent(sent: Boolean) : LiveData<List<Contact>>


    @Query("SELECT country FROM xmas_card_contact_table " +
            "GROUP BY country " +
            "ORDER BY firstName ASC")
    fun getCountries() : LiveData<List<Contact>>


    @Query("DELETE FROM xmas_card_contact_table")
    fun clearAll()

}