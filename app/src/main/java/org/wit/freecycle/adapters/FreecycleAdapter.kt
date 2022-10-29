package org.wit.freecycle.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.freecycle.activities.FreeCycleListActivity
import org.wit.freecycle.activities.ViewListingActivity
import org.wit.freecycle.databinding.CardFreecycleBinding
import org.wit.freecycle.models.FreecycleModel
import org.wit.freecycle.models.UserModel

interface FreecycleListener {
    fun onListingClick(listing: FreecycleModel)
}

class FreecycleAdapter constructor(private var listings: List<FreecycleModel>, private val listener: FreecycleListener) :
    RecyclerView.Adapter<FreecycleAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFreecycleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val listing = listings[holder.adapterPosition]
        holder.bind(listing, listener)
    }

    override fun getItemCount(): Int = listings.size

    class MainHolder(private val binding : CardFreecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

        fun bind(listing: FreecycleModel, listener: FreecycleListener) {
            binding.listingTitle.text = listing.listingTitle
            binding.name.text = listing.name
            Picasso.get().load(listing.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onListingClick(listing) }
            binding.editButton.setOnClickListener{
                val context = it.context.applicationContext
                val launcherIntent = Intent(context, ViewListingActivity::class.java)
                launcherIntent.putExtra("listing_edit", listing)
                refreshIntentLauncher.launch(launcherIntent)
            }
            // TODO how to register refresh callback
        }


        }

    }
}
