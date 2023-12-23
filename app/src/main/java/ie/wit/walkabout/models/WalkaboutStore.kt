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
                 walk: MutableLiveData<WalkaboutModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, walk: WalkaboutModel)
    fun delete(userid:String, walkid: String)
    fun update(userid:String, walkid: String, walk: WalkaboutModel)
}