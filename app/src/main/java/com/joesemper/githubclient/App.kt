package com.joesemper.githubclient

import android.app.Application
import com.joesemper.githubclient.di.AppComponent
import com.joesemper.githubclient.di.DaggerAppComponent
import com.joesemper.githubclient.di.modules.AppModule
import com.joesemper.githubclient.mvp.model.entity.room.Database
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}