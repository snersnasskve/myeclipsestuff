package com.ramblerswalks3.sners.kotlin01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun toastMe(view: View) {
        // val myToast = Toast.makeText(this, message, duration);
        val aToast = Toast.makeText(this, "Don't burn it", Toast.LENGTH_SHORT)
        aToast.show()
    }

    fun countMe(view: View) {
        //  Read the number on the screen
        var numberField = findViewById<TextView>(R.id.my_number)
        //  Add 1
        var stringVal = numberField.text.toString()
        var num = Integer.parseInt(stringVal) + 1
        //  Write the result to the screen
        numberField.text = num.toString()
    }

    fun randomMe(view: View) {
        var numberField = findViewById<TextView>(R.id.my_number)
        var stringVal = numberField.text.toString()
        var num = Integer.parseInt(stringVal)
        val randomIntent = Intent(this, SecondActivity::class.java)
        randomIntent.putExtra(SecondActivity.TOTAL_COUNT, num)
        startActivity(randomIntent)
    }
}
