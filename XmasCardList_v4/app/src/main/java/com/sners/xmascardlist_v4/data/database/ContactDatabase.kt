package com.sners.xmascardlist_v4.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDao : ContactDao
    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            //  Lock for multiple access
            synchronized(this) {
                ///WARNING: We don't actually want destructive migration - figure out the alternative
                //  You need to add the sequel commands to ALTER TABLE etc
                //  https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929

                //  Smart cast is only available to local variables - whatever that means
                var instance = INSTANCE
                if (null == instance)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "xmas_card_contact_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}