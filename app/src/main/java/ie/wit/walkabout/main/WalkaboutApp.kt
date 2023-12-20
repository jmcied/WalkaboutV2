package ie.wit.walkabout.main

import android.app.Application

import ie.wit.walkabout.models.WalkaboutStore
import timber.log.Timber

class WalkaboutApp : Application() {

    //lateinit var walksStore: WalkaboutStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //walksStore = WalkaboutMemStore()
        Timber.i("Starting Walkabout Application")
    }
}