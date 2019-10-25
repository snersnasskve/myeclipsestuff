package com.sners.aboutskania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.sners.aboutskania.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  //  private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        binding.doneButton.setOnClickListener {
//            addNickname(it)
        }
    }

    private fun addNickname(view: View)
    {
//        val viewEditText = findViewById<EditText>(R.id.nickname_edit)
//        val nicknameText = findViewById<TextView>(R.id.nickname_text)
//        binding.nicknameText.text = binding.nicknameEdit.text
//        viewEditText.visibility = View.GONE
//        binding.nicknameText.visibility = View.VISIBLE
    }
}
