package com.example.frontproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HiltApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}