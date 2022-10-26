package org.wit.freecycle.main

import android.app.Application
import org.wit.freecycle.models.*
import timber.log.Timber
import timber.log.Timber.i
import java.util.*
import kotlin.collections.ArrayList

class MainApp : Application() {

    // val listings = ArrayList<FreecycleModel>()
    // val listings = FreecycleMemStore()
    lateinit var listings: FreecycleStore
    lateinit var users: UserJSONStore
    var user: UserModel? = null


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        listings = FreecycleJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)

        i("FreeCycle app started")
        // seeding listings and users
//        listings.create(FreecycleModel(0,"Fiona Waters", "Washing Machine", "Zanuzzi xyz123 in working order", itemAvailable = false))
//        listings.create(FreecycleModel(1,"Mary Murphy", "Skateboard", "Zanuzzi xyz123 in working order", itemAvailable = true))
//        listings.create(FreecycleModel(2,"John O'Shea", "Kids Clothes", "Zanuzzi xyz123 in working order", itemAvailable = false))
//        users.create(UserModel(1, "Fiona", "Waters", "fiona@fiona.com", "password"))
//        users.create(UserModel(1, "Ciaran", "Waters", "ciaran@ciaran.com", "password"))
//        users.create(UserModel(1, "Homer", "Simpson", "homer@simpson.com", "password"))
//        i("list of users: {$users}")
//    }
    }
}