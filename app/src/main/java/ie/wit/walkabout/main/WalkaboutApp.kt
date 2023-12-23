package ie.wit.walkabout.main

import android.app.Application
import timber.log.Timber

class WalkaboutApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting Walkabout Application")
    }
}