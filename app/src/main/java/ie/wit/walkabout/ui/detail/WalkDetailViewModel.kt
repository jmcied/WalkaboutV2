package ie.wit.walkabout.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.walkabout.firebase.FirebaseDBManager
import ie.wit.walkabout.models.WalkaboutModel
import timber.log.Timber

class WalkDetailViewModel : ViewModel() {
    private val walk = MutableLiveData<WalkaboutModel>()

    var observableWalk: LiveData<WalkaboutModel>
        get() = walk
        set(value) {walk.value = value.value}

    fun getWalk(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, walk)
            Timber.i("Detail getWalk() Success : ${
                walk.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getWalk() Error : $e.message")
        }
    }

    fun updateWalk(userid:String, id: String,walk: WalkaboutModel) {
        try {
            FirebaseDBManager.update(userid, id, walk)
            Timber.i("Detail update() Success : $walk")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}