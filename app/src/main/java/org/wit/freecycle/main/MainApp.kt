package org.wit.freecycle.main

import android.app.Application
import org.wit.freecycle.models.*
import timber.log.Timber
import timber.log.Timber.i
import java.util.*
import kotlin.collections.ArrayList

class MainApp : Application() {

    lateinit var listings: FreecycleStore
    lateinit var users: UserJSONStore
    var user: UserModel? = null


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        listings = FreecycleJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)

        i("FreeCycle app started")
    }
}