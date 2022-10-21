package org.wit.freecycle.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.freecycle.R
import org.wit.freecycle.adapters.FreecycleAdapter
import org.wit.freecycle.adapters.FreecycleListener
import org.wit.freecycle.databinding.ActivityFreeCycleListBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel

class FreeCycleListActivity : AppCompatActivity(), FreecycleListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFreeCycleListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeCycleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FreecycleAdapter(app.listings.findAll(), this)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        loadListings()
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FreeCycleActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onListingClick(listing: FreecycleModel) {
        val launcherIntent = Intent(this, FreeCycleActivity::class.java)
        launcherIntent.putExtra("listing_edit", listing)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadListings() }
    }

    private fun loadListings() {
        showListings(app.listings.findAll())
    }

    fun showListings (listings: List<FreecycleModel>) {
        binding.recyclerView.adapter = FreecycleAdapter(listings, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

