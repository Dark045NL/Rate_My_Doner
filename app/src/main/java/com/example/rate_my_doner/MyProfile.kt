package com.example.rate_my_doner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.preference.PreferenceManager

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        // Retrieve the checked text from SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val checkedText = sharedPreferences.getString("checked_text", "")

        // Display the checked text in a TextView
        val textView = findViewById<TextView>(R.id.PreferenceFood)
        textView.text = checkedText

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {

            // Create an intent to navigate to SmaakProfielActivity
            val intent = Intent(this, SmaakProfielActivity::class.java)
            startActivity(intent)
        }
    }
}
