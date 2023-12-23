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
    var readOnly = MutableLiveData(false)

    init {
        load()
    }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,walksList)
            Timber.i("List Load Success : ${walksList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(walksList)
            Timber.i("List LoadAll Success : ${walksList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List LoadAll Error : $e.message")
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

