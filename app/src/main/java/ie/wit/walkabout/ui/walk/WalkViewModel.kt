package ie.wit.walkabout.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ie.wit.walkabout.models.WalkaboutManager
import ie.wit.walkabout.models.WalkaboutModel

class WalkViewModel {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addWalk(walk: WalkaboutModel) {
        status.value = try {
            WalkaboutManager.create(walk)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}