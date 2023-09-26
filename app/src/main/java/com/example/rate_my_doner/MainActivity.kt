package com.example.rate_my_doner

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.RatingBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.rate_my_doner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val MapButton = findViewById<Button>(R.id.FragmentBtn2)

        MapButton.setOnClickListener {
            // Create an Intent to start the new activity
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)


            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)


            setSupportActionBar(binding.appBarMain.toolbar)

            binding.appBarMain.fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            val drawerLayout: DrawerLayout = binding.drawerLayout
            val navView: NavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_Home, R.id.nav_Map, R.id.nav_Profile
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

        }
    }
}