package com.sners.ramblerswalks4

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import timber.log.Timber

//import com.sners.ramblerswalks4.databinding


// To add Timber
/*
1. Add the dependency in the app.gradle
2. Add RamblersApplicaton class and instantiate Timber in there
3. Add name = .RamblersApplication in manifest
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  Happens once at the beginning
        setContentView(R.layout.activity_main)
        //val binding = DataBindingUtil.setContentView<>(this, R.layout.activity_main)
       // val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Timber.i("onCreate called from karen")
    }

    override fun onResume() {
        super.onResume()
        //  Activity gains focus
        Timber.i("onResume called from karen")
    }

    override fun onStart() {
        super.onStart()
        //  Visible but not in focus
        Timber.i("onStart called from karen")
    }

    override fun onRestart() {
        super.onRestart()
        //  Called instead of onStart if onStart has already been called once
        Timber.i("onRestart called from karen")

    }

    override fun onPause() {
        super.onPause()
        //  Activity loses focus
        //  This blocks the UI so must be kept lightweight
        Timber.i("onPause called from karen")
    }

    override fun onStop() {
        super.onStop()
        //  Removed from the screen - no longer visible
        Timber.i("onStop called from karen")
    }

    override fun onDestroy() {
        super.onDestroy()
        //  Happens once at the very end
        Timber.i("onDestroy called from karen")

    }
}
