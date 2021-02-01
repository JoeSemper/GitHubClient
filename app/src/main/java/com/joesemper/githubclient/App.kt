package com.joesemper.githubclient

import android.app.Application
import com.joesemper.githubclient.mvp.model.entity.room.Database
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Database.create(this)
    }

    val navigatorHolder
        get() = cicerone.navigatorHolder

    val router
        get() = cicerone.router

}