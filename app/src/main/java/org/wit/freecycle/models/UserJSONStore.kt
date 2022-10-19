package org.wit.freecycle.models

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList
import java.lang.reflect.Type
import com.google.gson.*
import org.wit.freecycle.activities.FreeCycleActivity
import org.wit.freecycle.activities.FreeCycleListActivity
import org.wit.freecycle.helpers.exists
import org.wit.freecycle.helpers.read
import org.wit.freecycle.helpers.write
import timber.log.Timber
import timber.log.Timber.i

const val JSON_FILE2 = "users.json"
val gsonBuilder2: Gson = GsonBuilder().setPrettyPrinting()
    .create()
val listType2: Type = object : TypeToken<ArrayList<UserModel>>() {}.type
private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore (private val context: Context) : UserStore {
    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_FILE2)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        logAll()
        return users
    }

    override fun create(user: UserModel) {
        user.userId = generateRandomUserId()
        users.add(user)
        serialize()
    }

    override fun login(userEmail: String, userPassword: String)  {
        val users = findAll()
        for(user in users) {
            i("list of users :%v", users)
            if (user.userEmail == userEmail && user.userPassword == userPassword) {
                i("PASSWORD MATCHES")
            //go to freecycle list activity
            }
            i("NO MATCHING PASSWORD")
//            val text = "Invalid Details, Please Try Again!"
//            val duration = Toast.LENGTH_LONG
//            val toast = Toast.makeText(applicationContext, text, duration)
//            toast.show()
            // else return a toast stating invalid login details, please try again
        }
    }


// TODO use if allowing user to update details

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find{ u -> u.userId == user.userId}
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.lastName = user.lastName
            foundUser.userEmail = user.userEmail
            foundUser.userPassword = user.userPassword
            logAll()
        }
        serialize()
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder2.toJson(users, listType2)
        write(context, JSON_FILE2, jsonString)
    }
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE2)
        users = gsonBuilder2.fromJson(jsonString, listType2)
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }
}
