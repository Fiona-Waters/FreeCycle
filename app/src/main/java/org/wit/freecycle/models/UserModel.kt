package org.wit.freecycle.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (var userId: Long = 0, var firstName: String = "",
                      var lastName: String = "", var userEmail: String = "",
                      var userPassword: String = "") : Parcelable
