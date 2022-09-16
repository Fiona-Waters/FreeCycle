package org.wit.freecycle.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.freecycle.R
import org.wit.freecycle.databinding.ActivityFreeCycleListBinding
import org.wit.freecycle.databinding.CardFreecycleBinding
import org.wit.freecycle.main.MainApp
import org.wit.freecycle.models.FreecycleModel

class FreeCycleListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFreeCycleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeCycleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FreecycleAdapter(app.listings)

        binding.toolbar.title = title
     //   setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FreeCycleActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class FreecycleAdapter constructor(private var listings: List<FreecycleModel>) :
    RecyclerView.Adapter<FreecycleAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFreecycleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val listing = listings[holder.adapterPosition]
        holder.bind(listing)
    }

    override fun getItemCount(): Int = listings.size

    class MainHolder(private val binding : CardFreecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listing: FreecycleModel) {
            binding.name.text = listing.name
            binding.listingTitle.text = listing.listingTitle
        }
    }
}
