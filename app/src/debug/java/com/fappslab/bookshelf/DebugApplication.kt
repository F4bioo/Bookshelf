package com.fappslab.bookshelf

import com.fappslab.bookshelf.debugtools.FlipperSetup
import timber.log.Timber

class DebugApplication : ReleaseApplication() {

    override fun onCreate() {
        super.onCreate()
        FlipperSetup.start()
        Timber.plant(Timber.DebugTree())
    }
}
