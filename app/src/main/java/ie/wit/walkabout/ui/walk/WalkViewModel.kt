package ie.wit.walkabout.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.walkabout.firebase.FirebaseDBManager
import ie.wit.walkabout.firebase.FirebaseImageManager
import ie.wit.walkabout.models.WalkaboutModel
import timber.log.Timber

class WalkViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addWalk(firebaseUser: MutableLiveData<FirebaseUser>,
                walk: WalkaboutModel) {
        walk.profilepic = FirebaseImageManager.imageUri.value.toString()
        status.value = try {
            walk.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser,walk)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}