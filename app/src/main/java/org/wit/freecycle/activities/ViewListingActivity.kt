package org.wit.freecycle.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import com.squareup.picasso.Picasso
import org.wit.freecycle.R
import org.wit.freecycle.databinding.ActivityViewListingBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
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


// TODO set listing
        listing = intent.extras?.getParcelable("listing")!!
        binding.name.text = listing.name
        binding.contactNumber.text = listing.contactNumber
        binding.listingTitle.text = listing.listingTitle
        binding.listingDescription.text = listing.listingDescription
        binding.dateAvailable.text = listing.dateAvailable.toString()

        //TODO how to set pick up location - map or just lat lng
        //TODO have view map button which brings you to the map
        // activity with the saved location
        binding.pickupLocation.text = listing.lat.toString()

        Picasso.get()
            .load(listing.image)
            .into(binding.imageIcon)

        binding.itemAvailability.text = listing.itemAvailable.toString()

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
}