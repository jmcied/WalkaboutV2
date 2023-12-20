package ie.wit.walkabout.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AboutViewModel {
    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text
}
