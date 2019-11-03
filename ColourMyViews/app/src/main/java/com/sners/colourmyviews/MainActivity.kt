package com.sners.colourmyviews

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> =
            listOf(box_one_text, box_two_text, box_three_text, box_four_text, box_five_text,
                top_constraint_layout, red_button, green_button, yellow_button)

        for (item in clickableViews) {
            item.setOnClickListener { makeColoured(item)}

        }

    }

    private fun makeColoured(view: View) {
        when (view.id) {
            //  Boxes using Color class colours for background
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)

            //  Boxes using Android colour resources for background
            R.id.box_three_text -> view.setBackgroundColor(
                ContextCompat.getColor(applicationContext,
                    android.R.color.holo_green_light))
            R.id.box_four_text -> view.setBackgroundColor(
                ContextCompat.getColor(applicationContext,
                    android.R.color.holo_green_dark))
            R.id.box_five_text -> view.setBackgroundColor(
            ContextCompat.getColor(applicationContext,
                android.R.color.holo_green_light))

            //  Bottom buttons
            R.id.red_button -> box_three_text.setBackgroundColor(
                ContextCompat.getColor(applicationContext,
                R.color.my_red))
            R.id.green_button -> box_five_text.setBackgroundColor(
                ContextCompat.getColor(applicationContext,
                    R.color.my_green))
            R.id.yellow_button -> box_four_text.setBackgroundColor(
            ContextCompat.getColor(applicationContext,
                R.color.my_yellow))

            else -> view.setBackgroundColor(Color.LTGRAY)

        }
    }
}
