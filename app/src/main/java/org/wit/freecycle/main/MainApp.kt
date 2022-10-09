package org.wit.freecycle.main

import android.app.Application
import org.wit.freecycle.models.FreecycleMemStore
import org.wit.freecycle.models.FreecycleModel
import org.wit.freecycle.models.FreecycleJSONStore
import org.wit.freecycle.models.FreecycleStore
import timber.log.Timber
import timber.log.Timber.i
import java.util.*

class MainApp : Application() {

   // val listings = ArrayList<FreecycleModel>()
   // val listings = FreecycleMemStore()
    lateinit var listings: FreecycleStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        listings = FreecycleJSONStore(applicationContext)

        i("FreeCycle app started")
//        listings.create(FreecycleModel(0,"Fiona Waters", "Waterford", "X91YK7P", "Washing Machine", "Zanuzzi xyz123 in working order", itemAvailable = false))
//        listings.create(FreecycleModel(1,"Mary Murphy", "Waterford", "X91YK7P", "Skateboard", "Zanuzzi xyz123 in working order", itemAvailable = true))
//        listings.create(FreecycleModel(2,"John O'Shea", "Waterford", "X91YK7P", "Kids Clothes", "Zanuzzi xyz123 in working order", itemAvailable = false))
    }
}