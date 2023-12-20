package ie.wit.walkabout.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ie.wit.walkabout.models.WalkaboutManager
import ie.wit.walkabout.models.WalkaboutModel

class ListViewModel {
    private val walksList = MutableLiveData<List<WalkaboutModel>>()

    val observableDonationsList: LiveData<List<WalkaboutModel>>
        get() = walksList

    init {
        load()
    }

    fun load() {
        walksList.value = WalkaboutManager.findAll()
    }
}
