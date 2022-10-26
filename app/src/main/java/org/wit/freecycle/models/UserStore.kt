package org.wit.freecycle.models

import android.content.Context
import android.provider.ContactsContract

interface UserStore {

    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
    fun login(userEmail: String, userPassword: String): UserModel?
}