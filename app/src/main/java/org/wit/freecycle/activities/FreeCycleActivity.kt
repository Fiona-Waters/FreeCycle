package org.wit.freecycle.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.freecycle.R
import org.wit.freecycle.databinding.ActivityFreecycleBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
import timber.log.Timber.i


class FreeCycleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreecycleBinding
    var listing = FreecycleModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freecycle)

        i("FreeCycle Activity started..")

        binding = ActivityFreecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Freecycle activity started")
        binding.btnAdd.setOnClickListener() {
            listing.name = binding.name.text.toString()
            listing.location = binding.location.text.toString()
            listing.eircode = binding.eircode.text.toString()
            listing.listingTitle = binding.listingTitle.text.toString()
            listing.listingDescription = binding.listingDescription.text.toString()

            if (listing.listingTitle.isNotEmpty() && listing.listingDescription.isNotEmpty() && listing.name.isNotEmpty() && listing.location.isNotEmpty() && listing.eircode.isNotEmpty()) {
                app.listings.add(listing.copy())
                i("add Button Pressed: $listing")
                for (i in app.listings.indices) {
                    i("Freecycle[$i]:${this.app.listings[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"All fields are required", Snackbar.LENGTH_LONG)
                    .show()
            }
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
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}