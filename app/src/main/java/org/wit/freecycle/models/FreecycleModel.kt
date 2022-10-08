package org.wit.freecycle.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FreecycleModel(var id: Long = 0, var name: String = "",
                          var location: String = "", var eircode: String = "",
                          var listingTitle: String = "", var listingDescription: String = "",
                          var image: Uri = Uri.EMPTY, var itemAvailable: Boolean = true   ) : Parcelable
