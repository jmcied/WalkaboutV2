package ie.wit.walkabout.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.walkabout.models.WalkaboutModel
import ie.wit.walkabout.models.WalkaboutStore
import timber.log.Timber

object FirebaseDBManager : WalkaboutStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(walksList: MutableLiveData<List<WalkaboutModel>>) {
        database.child("walks")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Walk error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<WalkaboutModel>()
                    val children = snapshot.children
                    children.forEach {
                        val walk = it.getValue(WalkaboutModel::class.java)
                        localList.add(walk!!)
                    }
                    database.child("walks")
                        .removeEventListener(this)

                    walksList.value = localList
                }
            })
    }


    override fun findAll(userid: String, walksList: MutableLiveData<List<WalkaboutModel>>) {

        database.child("user-walks").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Walk error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<WalkaboutModel>()
                    val children = snapshot.children
                    children.forEach {
                        val walk = it.getValue(WalkaboutModel::class.java)
                        localList.add(walk!!)
                    }
                    database.child("user-walks").child(userid)
                        .removeEventListener(this)

                    walksList.value = localList
                }
            })
    }
    override fun findById(userid: String, walkid: String, walk: MutableLiveData<WalkaboutModel>) {
        database.child("user-walks").child(userid)
            .child(walkid).get().addOnSuccessListener {
                walk.value = it.getValue(WalkaboutModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, walk: WalkaboutModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("walks").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        walk.uid = key
        val walkValues = walk.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/walks/$key"] = walkValues
        childAdd["/user-walks/$uid/$key"] = walkValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, walkid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/walks/$walkid"] = null
        childDelete["/user-walks/$userid/$walkid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, walkid: String, walk: WalkaboutModel) {

        val walkValues = walk.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["walks/$walkid"] = walkValues
        childUpdate["user-walks/$userid/$walkid"] = walkValues

        database.updateChildren(childUpdate)
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userWalks = database.child("user-walks").child(userid)
        val allWalks = database.child("walks")

        userWalks.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all walks that match 'it'
                        val walk = it.getValue(WalkaboutModel::class.java)
                        allWalks.child(walk!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }
}