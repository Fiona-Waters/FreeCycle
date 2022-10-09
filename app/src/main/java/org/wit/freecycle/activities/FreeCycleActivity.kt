package org.wit.freecycle.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.freecycle.R
import org.wit.freecycle.databinding.ActivityFreecycleBinding
import org.wit.freecycle.helpers.showImagePicker
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
import timber.log.Timber.i
import java.util.*


class FreeCycleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreecycleBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var listing = FreecycleModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        var edit = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freecycle)

        i("FreeCycle Activity started..")
      //  binding.deleteListing.setEnabled(false)
        binding = ActivityFreecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        app = application as MainApp
        i("Freecycle activity started")

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

//        val datePicker = findViewById<DatePicker>(R.id.date_Picker)
//        val today = Calendar.getInstance()
//        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//            today.get(Calendar.DAY_OF_MONTH)
//
//        ) { view, year, month, day ->
//            val month = month + 1
//            val msg = "You Selected: $day/$month/$year"
//            Toast.makeText(this@FreeCycleActivity, msg, Toast.LENGTH_SHORT).show()
//        }

        binding.btn.setOnClickListener {
            val datePicker = findViewById<DatePicker>(R.id.datePicker)
            Toast.makeText(this,"${datePicker.year} / ${datePicker.month} / ${datePicker.dayOfMonth}",Toast.LENGTH_LONG).show()
            val tv = findViewById<TextView>(R.id.tv)
            tv.text = "${datePicker.year} / ${datePicker.month} / ${datePicker.dayOfMonth}"
          // TODO bind date to dateAvailable variable (also add this variable)
            // TODO also include in update
        }


        if (intent.hasExtra("listing_edit")) {
            edit = true
            listing = intent.extras?.getParcelable("listing_edit")!!
            binding.listingTitle.setText(listing.listingTitle)
            binding.listingDescription.setText(listing.listingDescription)
            binding.name.setText(listing.name)
            binding.location.setText(listing.location)
            binding.eircode.setText(listing.eircode)
            binding.toggleButton.isChecked = listing.itemAvailable
            binding.btnAdd.setText(R.string.save_listing)
            binding.deleteListing.setText(R.string.button_delete_listing)

            // only show delete listing button in edit view
            binding.deleteListing.setVisibility(View.VISIBLE)

            Picasso.get()
                .load(listing.image)
                .into(binding.ListingImage)
            if (listing.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.edit_image)
            }
        }
        binding.btnAdd.setOnClickListener() {
            listing.name = binding.name.text.toString()
            listing.location = binding.location.text.toString()
            listing.eircode = binding.eircode.text.toString()
            listing.listingTitle = binding.listingTitle.text.toString()
            listing.listingDescription = binding.listingDescription.text.toString()
            listing.itemAvailable = binding.toggleButton.isChecked

            if (listing.listingTitle.isNotEmpty() && listing.listingDescription.isNotEmpty() && listing.name.isNotEmpty() && listing.location.isNotEmpty() && listing.eircode.isNotEmpty()) {
                if(edit) {
                    app.listings.update(listing.copy())
                } else {
                    app.listings.create(listing.copy())
                    i("add Button Pressed: $listing")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,R.string.all_fields_required, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.deleteListing.setOnClickListener() {
            app.listings.delete(listing)
            i("delete button pressed: $listing")
            setResult(RESULT_OK)
            finish()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            listing.image = result.data!!.data!!
                            Picasso.get()
                                .load(listing.image)
                                .into(binding.ListingImage)
                            binding.chooseImage.setText(R.string.edit_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }


    }



}