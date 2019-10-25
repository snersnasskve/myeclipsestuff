package com.ramblerswalks3.sners.kotlin01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

class SecondActivity : AppCompatActivity() {
    companion object {
        const val TOTAL_COUNT = "total_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        showRandomNumber()
    }

    fun showRandomNumber() {
        val count = intent.getIntExtra(TOTAL_COUNT, 0)

        //  generate the random number
        val random = Random()
        var randomInt = 0
        if (count > 0)
        {
            randomInt = random.nextInt(count + 1)
        }

        var labelString = findViewById<TextView>(R.id.randomIntro)
        var randomString = findViewById<TextView>(R.id.randomNo)

        labelString.text = getString(R.string.random_intro, count)
        randomString.text = randomInt.toString()

    }
}
