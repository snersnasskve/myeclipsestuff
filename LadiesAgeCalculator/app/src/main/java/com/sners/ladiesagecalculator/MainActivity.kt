package com.sners.ladiesagecalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    /**
     * @property calculateButton The button to calculate your age
     */
    private var calculateButton: Button? = null

    /**
     * @property ageCalculated The resulting age text and description
     */
    private var ageCalculated: TextView? = null

    /**
     * @property birthYear Day of birthday
     */
    private var birthDay: EditText? = null

    /**
     * @property birthMonth Month of birthday
     */
    private var birthMonth: AutoCompleteTextView? = null

    /**
     * @property birthYear Year of birthday
     */
    private var birthYear: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {

// To auto complete the month
        // http://saigeethamn.blogspot.com/2010/05/auto-complete-text-view-android.html

        super.onCreate(savedInstanceState)
        setContentView(R.layout.age_calc)
        calculateButton = findViewById(R.id.calculate_button) as Button
        ageCalculated = findViewById(R.id.age_calculated) as TextView
        birthDay = findViewById(R.id.edit_day) as EditText
        birthYear = findViewById(R.id.edit_year) as EditText

        val monthNames =resources.getStringArray(R.array.months)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monthNames)

        birthMonth = findViewById(R.id.edit_month) as AutoCompleteTextView
        birthMonth!!.setThreshold(1)
        birthMonth!!.setAdapter(adapter)




        calculateButton!!.setOnClickListener {
            calculateAge()
           }
    }

    private fun calculateAge() {
        ageCalculated!!.setText(getString(R.string.fabulous))
        Toast.makeText(this, "You pressed calc", Toast.LENGTH_LONG).show()

    }
}