package ie.wit.walkabout.models

interface WalkaboutStore {
    fun findAll(): List<WalkaboutModel>
    fun create(walk: WalkaboutModel)
    fun update(walk: WalkaboutModel)
    fun delete(walk: WalkaboutModel)
    fun findById(id:Long) : WalkaboutModel?
}