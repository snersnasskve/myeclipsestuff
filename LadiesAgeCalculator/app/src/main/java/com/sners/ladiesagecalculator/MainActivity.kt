package com.sners.ladiesagecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    /**
     * @property calculateButton The button to calculate your age
     */
    private var calculateButton: Button? = null

    /**
     * @property ageCalculated The resulting age text and description
     */
    private var ageCalculated: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calculateButton = findViewById(R.id.calculate_button) as Button
        ageCalculated = findViewById(R.id.age_calculated) as TextView

        calculateButton!!.setOnClickListener {
            calculateAge()
           }
    }

    private fun calculateAge() {
        ageCalculated!!.setText(getString(R.string.fabulous))
        Toast.makeText(this, "You pressed calc", Toast.LENGTH_LONG).show()

    }
}