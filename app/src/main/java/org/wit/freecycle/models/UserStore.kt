package org.wit.freecycle.models

import android.provider.ContactsContract

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun login(userEmail: UserModel, userPassword: UserModel)
}