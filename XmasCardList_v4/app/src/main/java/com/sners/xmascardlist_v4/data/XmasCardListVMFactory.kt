package com.sners.xmascardlist_v4.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sners.xmascardlist_v4.data.database.ContactDao
import java.lang.IllegalArgumentException

class XmasCardListVMFactory (
    private val dataSource: ContactDao,
    private val application: Application) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(XmasCardListVM::class.java))
        {
            return XmasCardListVM(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class (top level)")
    }
}