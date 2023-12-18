package ie.wit.walkabout.main

import android.app.Application
import ie.wit.walkabout.models.WalkaboutMemStore
import ie.wit.walkabout.models.WalkaboutStore
import timber.log.Timber

lateinit var walksStore: WalkaboutStore
class WalkaboutApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        walksStore = WalkaboutMemStore()
        Timber.i("Starting Walkabout Application")
    }
}