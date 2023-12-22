package ie.wit.walkabout.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.walkabout.firebase.FirebaseDBManager
import ie.wit.walkabout.models.WalkaboutModel
import timber.log.Timber
import java.lang.Exception

class ListViewModel : ViewModel() {
    private val walksList = MutableLiveData<List<WalkaboutModel>>()

    val observableWalksList: LiveData<List<WalkaboutModel>>
        get() = walksList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,walksList)
            Timber.i("Report Load Success : ${walksList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("List Delete Success")
        }
        catch (e: Exception) {
            Timber.i("List Delete Error : $e.message")
        }
    }
}

