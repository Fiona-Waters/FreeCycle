package org.wit.freecycle.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.freecycle.helpers.*
import timber.log.Timber
import java.lang.Exception
import java.lang.reflect.Type
import java.time.LocalDate
import java.util.*

const val JSON_FILE = "listings.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(LocalDate::class.java, LocalDateParser())
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<FreecycleModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FreecycleJSONStore(private val context: Context) : FreecycleStore {

    var listings = mutableListOf<FreecycleModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FreecycleModel> {
        logAll()
        return listings
    }

    override fun create(listing: FreecycleModel) {
        listing.id = generateRandomId()
        listings.add(listing)
        serialize()
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
        serialize()
    }

    override fun delete(listing: FreecycleModel) {
        listings.remove(listing)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(listings, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        listings = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        listings.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

class LocalDateParser : JsonDeserializer<LocalDate>,JsonSerializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return LocalDate.parse(json?.asString)
    }

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}