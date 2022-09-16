package org.wit.freecycle.main

import android.app.Application
import org.wit.freecycle.models.FreecycleModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val listings = ArrayList<FreecycleModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("FreeCycle app started")
//        listings.add(FreecycleModel("Fiona Waters", "Waterford", "X91YK7P", "Washing Machine", "Zanuzzi xyz123 in working order"))
//        listings.add(FreecycleModel("Mary Murphy", "Waterford", "X91YK7P", "Skateboard", "Zanuzzi xyz123 in working order"))
//        listings.add(FreecycleModel("John O'Shea", "Waterford", "X91YK7P", "Kids Clothes", "Zanuzzi xyz123 in working order"))
    }
}