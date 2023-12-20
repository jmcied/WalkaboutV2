package ie.wit.walkabout.models

import timber.log.Timber
import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
object WalkaboutManager : WalkaboutStore {

    val walks = ArrayList<WalkaboutModel>()

    override fun findAll(): List<WalkaboutModel> {
        return walks
    }

    override fun findById(id: Long): WalkaboutModel? {
        val foundWalk: WalkaboutModel? = walks.find { it.id == id }
        return foundWalk
    }

    override fun create(walk: WalkaboutModel) {
        walk.id = getId()
        walks.add(walk)
        logAll()
    }

    override fun update(walk: WalkaboutModel) {
        var foundWalk: WalkaboutModel? = walks.find { w -> w.id == walk.id }
        if (foundWalk != null) {
            foundWalk.title = walk.title
            foundWalk.difficulty = walk.difficulty
            foundWalk.terrain = walk.terrain

            logAll()
        }
    }

    override fun delete(walk: WalkaboutModel) {
        walks.remove(walk)
    }
    fun logAll() {
        Timber.v("** Walks List **")
        walks.forEach{ i("${it}") }
    }


}