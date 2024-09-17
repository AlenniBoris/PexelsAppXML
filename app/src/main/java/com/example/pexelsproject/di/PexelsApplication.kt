package com.example.pexelsproject.di

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PexelsApplication: Application(){
    companion object {
        lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
    }

    override fun onCreate() {
        super.onCreate()
        cicerone = Cicerone.create()
        router = cicerone.router
    }
}