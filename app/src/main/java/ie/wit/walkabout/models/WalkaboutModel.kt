package ie.wit.walkabout.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkaboutModel(
    var uid: String = "",
    var title: String = "N/A",
    var terrain: String = "N/A",
    var difficulty: String = "N/A",
    var email: String = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "terrain" to terrain,
            "difficulty" to difficulty,
            "email" to email

            )
    }
}