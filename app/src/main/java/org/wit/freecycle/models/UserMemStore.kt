package org.wit.freecycle.models

import timber.log.Timber.i

var lastUserId = 0L

internal fun getUserId(): Long {
    return lastUserId++
}

abstract class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

     override fun create(user: UserModel) {
         user.userId = getId()
         users.add(user)
         logAll()
    }

      fun login(userEmail: UserModel, userPassword: UserModel ) {
        // TODO if the users password matches then log them in i.e. move to freecycleListActivity
        // TODO only view their own listings?
    }
    // TODO

   override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find{ u -> u.userId == user.userId}
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.lastName = user.lastName
            foundUser.userEmail = user.userEmail
            foundUser.userPassword = user.userPassword
            logAll()
        }
    }

    override fun delete( user: UserModel) {
        users.remove(user)
        logAll()
    }

    fun logAll() {
        users.forEach{i("${it}")}
    }
}
