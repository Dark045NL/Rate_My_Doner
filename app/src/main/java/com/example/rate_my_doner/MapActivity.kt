package com.example.rate_my_doner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

// Import statements and class declaration
class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null // GoogleMap object to interact with the map
    private lateinit var autocompleteFragment: AutocompleteSupportFragment // Autocomplete fragment for place search

    // This function is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map) // Set the layout for this activity

        // Initialize the Places SDK with your API key
        Places.initialize(applicationContext, getString(R.string.google_map_api_key))

        // Find the AutocompleteSupportFragment by its ID and configure it
        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {
                // Handle errors in place selection
                Toast.makeText(this@MapActivity, "Some error in Search", Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceSelected(place: Place) {
                // Handle the selected place and zoom the map
                val latLng = place.latLng!!
                zoomOnMap(latLng)
            }
        })

        // Find the SupportMapFragment by its ID and initialize the map asynchronously
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Find the map option button and set up a PopupMenu for changing map types
        val mapOptionButton: ImageButton = findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(this, mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.map_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId) // Change the map type based on the selected menu item
            true
        }

        // Show the PopupMenu when the map option button is clicked
        mapOptionButton.setOnClickListener {
            popupMenu.show()
        }
    }

    // Function to zoom the map to a specific LatLng location
    private fun zoomOnMap(latLng: LatLng) {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(latLng, 18f) // 18f = zoom level
        mGoogleMap?.animateCamera(newLatLngZoom)
    }

    // Function to change the map type based on the selected menu item
    private fun changeMap(itemId: Int) {
        when (itemId) {
            R.id.normal_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellite_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
    }

    // Callback function invoked when the map is ready
    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap // Initialize the GoogleMap object
    }
}