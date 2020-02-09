package com.sners.myfavouriterobot.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.sners.myfavouriterobot.R
import com.sners.myfavouriterobot.utilities.FileHelper

//  If you need a reference to the application, then rather derive off AndroidViewModel
class MainViewModel(app:Application) : AndroidViewModel(app) {

    init {
        val text = FileHelper.getTextFromResources(app, R.raw.robots)
        Log.i("RobotLogging", text)

    }

}