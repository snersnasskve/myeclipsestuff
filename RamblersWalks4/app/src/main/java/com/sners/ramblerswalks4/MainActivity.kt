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
        setContentView(R.layout.activity_main)
        //val binding = DataBindingUtil.setContentView<>(this, R.layout.activity_main)
       // val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Timber.i("onCreate called from karen")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called from karen")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called from karen")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called from karen")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop called from karen")
    }
}
