package org.wit.freecycle.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import org.wit.freecycle.R
import org.wit.freecycle.databinding.ActivityViewListingBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
import org.wit.freecycle.models.Location
import timber.log.Timber.i


class ViewListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewListingBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    var listing = FreecycleModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_listing)

        i("View Listing Activity Started...")
        binding = ActivityViewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        app = application as MainApp

        listing = intent.extras?.getParcelable("listing")!!
        binding.name.text = listing.name
        binding.contactNumber.text = listing.contactNumber
        binding.listingTitle.text = listing.listingTitle
        binding.listingDescription.text = listing.listingDescription
        binding.dateAvailable.text = listing.dateAvailable.toString()

        binding.viewMap.setOnClickListener {
            val lat = listing.lat
            val lng = listing.lng
            val zoom = listing.zoom
            val location = Location(lat, lng, zoom)
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerMapCallback()

        val image = findViewById<ImageView>(R.id.imageIcon)
        if (listing.image != Uri.EMPTY) {
            image.background = null
        }
        Picasso.get()
            .load(listing.image)
            .into(binding.imageIcon)

        val available = "Available"
        val unavailable = "Unavailable"
        if (listing.itemAvailable) {
            binding.itemAvailability.text = available
        } else {
            binding.itemAvailability.text = unavailable
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.listing_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel_button -> {
                val launcherIntent = Intent(this, FreeCycleListActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
            }
    }
}