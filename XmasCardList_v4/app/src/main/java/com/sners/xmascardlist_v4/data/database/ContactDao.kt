package com.sners.xmascardlist_v4.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

//  Dao is how you access a database. This auto-generates the sqlite qeries in most cases
//  NB this is an interface not a class
@Dao
interface ContactDao {

    @Insert
    fun insert(night: Contact)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    fun update(night: Contact)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long): Contact?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<Contact>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): Contact?
//    @Insert
//    fun insert(contact: Contact)
//
//    @Update
//    fun update(contact: Contact)
//
//    @Delete
//    fun delete(contact: Contact)
//
//    /**
//     * Selects and returns the latest night.
//     */
//    @Query("SELECT * FROM xmas_card_contact_table ORDER BY contactId DESC LIMIT 1")
//    fun getTonight(): Contact?


//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "ORDER BY firstName ASC")
//    fun getAllContacts(): LiveData<List<Contact>>
//
//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "WHERE contactId = :key")
//    fun getContact(key: Long): Contact
//
//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "WHERE favourite = true " +
//            "ORDER BY firstName ASC")
//    fun getFavourites() : LiveData<List<Contact>>
//
//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "WHERE country = ':country' " +
//            "ORDER BY firstName ASC")
//    fun getForCountry(country: String) : LiveData<List<Contact>>
//
//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "WHERE country = ':year' " +
//            "ORDER BY firstName ASC")
//    fun getForXmasReceived(year: String) : LiveData<List<Contact>>
//
//    @Query("SELECT * FROM xmas_card_contact_table " +
//            "WHERE xmas_card_sent = :sent " +
//            "ORDER BY firstName ASC"    )
//    fun getForXmasSent(sent: Boolean) : LiveData<List<Contact>>
//
//
//    @Query("SELECT country FROM xmas_card_contact_table " +
//            "GROUP BY country " +
//            "ORDER BY firstName ASC")
//    fun getCountries() : LiveData<List<Contact>>


//    @Query("DELETE FROM xmas_card_contact_table")
//    fun clearAll()

}