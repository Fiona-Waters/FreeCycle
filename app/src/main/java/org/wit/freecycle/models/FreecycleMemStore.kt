package org.wit.freecycle.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class FreecycleMemStore : FreecycleStore {

    val listings = ArrayList<FreecycleModel>()

    override fun findAll(): List<FreecycleModel> {
        return listings
    }

    override fun create(listing: FreecycleModel) {
        listing.id = getId()
        listings.add(listing)
        logAll()
    }

    override fun update(listing: FreecycleModel) {
        var foundListing: FreecycleModel? = listings.find{ l -> l.id == listing.id}
        if (foundListing != null) {
            foundListing.name = listing.name
            foundListing.listingTitle = listing.listingTitle
            foundListing.listingDescription = listing.listingDescription
            foundListing.image = listing.image
            foundListing.itemAvailable = listing.itemAvailable
            foundListing.dateAvailable = listing.dateAvailable
            logAll()
        }
    }

    override fun delete(listing: FreecycleModel) {
        listings.remove(listing)
        logAll()
    }

    fun logAll() {
        listings.forEach{ i("${it}")}
    }
}
