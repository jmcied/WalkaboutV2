package ie.wit.walkabout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkaboutModel(
    var id: Long = 0,
    var title: String = "N/A",
    var terrain: String = "N/A",
    var difficulty: String = "N/A") : Parcelable