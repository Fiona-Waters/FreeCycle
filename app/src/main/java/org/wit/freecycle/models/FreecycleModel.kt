package org.wit.freecycle.models

import android.net.Uri
import android.os.Parcelable
import android.widget.DatePicker
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
data class FreecycleModel(var id: Long = 0, var name: String = "",
                          var location: String = "", var eircode: String = "",
                          var listingTitle: String = "", var listingDescription: String = "",
                          var image: Uri = Uri.EMPTY, var itemAvailable: Boolean = true, var dateAvailable: LocalDate = LocalDate.now()   ) : Parcelable
