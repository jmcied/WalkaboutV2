package ie.wit.walkabout.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface WalkaboutStore {
    fun findAll(walksList:
                MutableLiveData<List<WalkaboutModel>>)
    fun findAll(userid:String,
                walksList:
                MutableLiveData<List<WalkaboutModel>>)
    fun findById(userid:String, walkid: String,
                 donation: MutableLiveData<WalkaboutModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, walk: WalkaboutModel)
    fun delete(userid:String, walkidid: String)
    fun update(userid:String, walkidid: String, walkid: WalkaboutModel)
}