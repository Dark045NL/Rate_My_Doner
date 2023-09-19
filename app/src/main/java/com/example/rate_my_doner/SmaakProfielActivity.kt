package com.example.rate_my_doner


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import android.content.Intent
import android.widget.Button
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceCategory

class SmaakProfielActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myprofile_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val button = findViewById<Button>(R.id.SaveBtn)
        button.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)

            // Retrieve the selected preferences from SharedPreferences
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val selectedPreferences = sharedPreferences.getString("checked_text", "")

            // Pass the selected preferences to MyProfile activity
            intent.putExtra("selected_preferences", selectedPreferences)

            startActivity(intent)
        }


    }

    // Rest of your code
}

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // Get the SharedPreferences instance from the activity's context
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Get the root PreferenceScreen
        val preferenceScreen = preferenceScreen

        // Add an OnPreferenceChangeListener to each CheckBoxPreference
        for (i in 0 until preferenceScreen.preferenceCount) {
            val preferenceCategory = preferenceScreen.getPreference(i) as PreferenceCategory
            for (j in 0 until preferenceCategory.preferenceCount) {
                val checkBoxPreference = preferenceCategory.getPreference(j) as CheckBoxPreference

                checkBoxPreference.setOnPreferenceChangeListener { _, newValue ->
                    if (newValue is Boolean) {
                        // Checkbox state changed
                        val title = checkBoxPreference.title.toString()

                        if (newValue) {
                            // Checkbox is checked, save the text to SharedPreferences
                            val currentText = sharedPreferences.getString("checked_text", "")
                            val newText = if (currentText.isNullOrEmpty()) {
                                title
                            } else {
                                "$currentText, $title"
                            }
                            sharedPreferences.edit().putString("checked_text", newText).apply()
                        } else {
                            // Checkbox is unchecked, update the text in SharedPreferences
                            val currentText = sharedPreferences.getString("checked_text", "")
                            val newText = currentText?.replace("$title, ", "")?.replace(title, "")
                            sharedPreferences.edit().putString("checked_text", newText).apply()
                        }
                    }

                    true
                }
            }
        }
    }
}