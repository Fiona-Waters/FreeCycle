package org.wit.freecycle.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.freecycle.R
import org.wit.freecycle.adapters.FreecycleAdapter
import org.wit.freecycle.adapters.FreecycleListener
import org.wit.freecycle.databinding.ActivityFreeCycleListBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel
import org.wit.freecycle.models.Location
import java.util.*

class FreeCycleListActivity : AppCompatActivity(), FreecycleListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFreeCycleListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var searchListings = mutableListOf<FreecycleModel>()

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
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.search_button)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                searchListings.clear()
                var newArrayList = app.listings.findAll()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    newArrayList.forEach {
                        if (it.listingTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchListings.add(it)
                        }
                    }
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    searchListings.addAll(newArrayList)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
                return false
            }
        })
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
        // if
        val launcherIntent = Intent(this, ViewListingActivity::class.java)
        //launcherIntent.putExtra("listing_edit", listing)

        launcherIntent.putExtra("listing", listing)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadListings() }
    }

    private fun loadListings() {
        searchListings.clear()
        searchListings.addAll(app.listings.findAll())
        showListings()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showListings () {
        binding.recyclerView.adapter = FreecycleAdapter(searchListings, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

