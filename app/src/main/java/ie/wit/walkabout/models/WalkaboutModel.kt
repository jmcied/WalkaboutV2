package ie.wit.walkabout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkaboutModel(
    var id: Long = 0,
    var title: String = "",
    var difficulty: String = "N/A",
    var terrain: String = "N/A") : Parcelable