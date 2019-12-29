package com.sners.xmascardlist_v4.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xmas_card_contact_table")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L,
    @ColumnInfo(name = "first_name")
    val firstName: String = "",
    @ColumnInfo(name = "email_address")
    val emailAddress : String = "",
    @ColumnInfo(name = "address")
    val address : String = "",
    @ColumnInfo(name = "area_code")
    val areaCode : String = "",
    @ColumnInfo(name = "country")
    val country : String = "",
    @ColumnInfo(name = "kids")
    val kids : String = "",
    @ColumnInfo(name = "xmas_card_received")
    val lastSent : String = "",
    @ColumnInfo(name = "xmas_card_received")
    val xmasCardReceived : String = "",
    @ColumnInfo(name = "xmas_card_sent")
    val xmasCardSent: Boolean = false,
    @ColumnInfo(name = "xmas_email")
    val xmasEmail : Boolean = false,
    @ColumnInfo(name = "favourite")
    val favourite : Boolean = false


)