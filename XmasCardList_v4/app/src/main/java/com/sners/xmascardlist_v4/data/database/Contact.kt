package com.sners.xmascardlist_v4.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "xmas_card_contact_table")
@Entity(tableName = "daily_sleep_quality_table")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "quality_rating")
    var sleepQuality: Int = -1

//    @PrimaryKey(autoGenerate = true)
//    var contactId: Long = 0L,

//    @ColumnInfo(name = "first_name")
//    val firstName: String = "",
//    @ColumnInfo(name = "email_address")
//    val emailAddress : String = "",
//    @ColumnInfo(name = "address")
//    val address : String = "",
//    @ColumnInfo(name = "area_code")
//    val areaCode : String = "",
//    @ColumnInfo(name = "country")
//    val country : String = "",
//    @ColumnInfo(name = "kids")
//    val kids : String = "",
//    @ColumnInfo(name = "xmas_card_received")
//    val lastSent : String = "",
//    @ColumnInfo(name = "xmas_card_received")
//    val xmasCardReceived : String = "",
//    @ColumnInfo(name = "xmas_card_sent")
//    val xmasCardSent: Boolean = false,
//    @ColumnInfo(name = "xmas_email")
//    val xmasEmail : Boolean = false,
//    @ColumnInfo(name = "favourite")
//    val favourite : Boolean = false


)