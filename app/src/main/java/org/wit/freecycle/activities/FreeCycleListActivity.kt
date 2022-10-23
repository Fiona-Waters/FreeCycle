package org.wit.freecycle.activities

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.freecycle.R
import org.wit.freecycle.adapters.FreecycleAdapter
import org.wit.freecycle.adapters.FreecycleListener
import org.wit.freecycle.databinding.ActivityFreeCycleListBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
import timber.log.Timber.i
import java.util.*
import kotlin.collections.ArrayList

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

        i("INTENT")
        if(Intent.ACTION_SEARCH == intent.action) {
            i("SEARCH")
            intent.getStringExtra(SearchManager.QUERY)?.also { query -> doMySearch(query)}
        }
    }

    fun doMySearch(query : String) {
        //TODO if query matches something in title
        // of listings array then show this/these items
        i("search query %v", query)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
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

    override fun onSearchRequested(): Boolean {
        return super.onSearchRequested()
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

    private fun showListings (listings: List<FreecycleModel>) {
        binding.recyclerView.adapter = FreecycleAdapter(listings, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

