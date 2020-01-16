package com.sners.xmascardlist_v4

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sners.xmascardlist_v4.data.database.*

import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class ContactDatabaseTest {
    private lateinit var contactDao: ContactDao
    private lateinit var db: ContactDatabase

    @Before
    fun createDB() {

        val fred = ContactDatabase.getFred()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        contactDao = db.contactDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetContact() {
       val contact = Contact()
        contactDao.insert(contact)
        val egContact = contactDao.getTonight()
        Assert.assertEquals(egContact?.sleepQuality, -1)

    }

}

