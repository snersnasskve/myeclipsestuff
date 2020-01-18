package com.sners.xmascardlist_v4.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sners.xmascardlist_v4.data.database.ContactDao

class XmasCardListVM (
    val database: ContactDao,
    application: Application) : AndroidViewModel(application){
}