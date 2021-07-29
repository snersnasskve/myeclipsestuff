package com.sners.ladiesagecalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sners.ladiesagecalculator.databinding.AgeCalcBinding


class MainActivity : AppCompatActivity() {

    /**
     * @property binding The binding class is autogenerated from the name of the layout it refers to
     * 1. put the xml into a layout tag
     * 2. add this field
     * 3. replace setContentView with DataBindingUtil.setContentView
     */
    private lateinit var binding: AgeCalcBinding

    /**
     * @property age Age object
     */
    private var age : Age = Age()



    override fun onCreate(savedInstanceState: Bundle?) {

        // To auto complete the month
        // http://saigeethamn.blogspot.com/2010/05/auto-complete-text-view-android.html

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.age_calc)
        binding.age = age

        val monthNames = resources.getStringArray(R.array.months)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monthNames)

        binding.editMonth.threshold = 1
        binding.editMonth.setAdapter(adapter)

        binding.calculateButton.setOnClickListener {
            calculateAge()
        }

    }

    private fun calculateAge() {
        binding.ageCalculated.setText(getString(R.string.fabulous))
        Toast.makeText(this, "You pressed calc", Toast.LENGTH_LONG).show()
        binding.invalidateAll()
    }
}