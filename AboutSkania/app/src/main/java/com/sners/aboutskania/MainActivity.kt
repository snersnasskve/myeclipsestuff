package com.sners.aboutskania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.sners.aboutskania.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.doneButton.setOnClickListener {
            addNickname(it)
        }
    }

    private fun addNickname(view: View)
    {
        binding.apply {
            nicknameText.text = nicknameEdit.text
            invalidateAll() //  To force refresh
            nicknameEdit.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
            doneButton.visibility = View.GONE
        }


    }
}
