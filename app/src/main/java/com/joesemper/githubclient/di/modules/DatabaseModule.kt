package com.joesemper.githubclient.di.modules

import androidx.room.Room
import com.joesemper.githubclient.App
import com.joesemper.githubclient.mvp.model.cache.IGithubRepositoriesCache
import com.joesemper.githubclient.mvp.model.cache.IGithubUsersCache
import com.joesemper.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache
import com.joesemper.githubclient.mvp.model.cache.room.RoomGithubUsersCache
import com.joesemper.githubclient.mvp.model.entity.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun usersCache(database: Database): IGithubUsersCache {
        return RoomGithubUsersCache(database)
    }

    @Singleton
    @Provides
    fun repositoriesCache(database: Database): IGithubRepositoriesCache {
        return RoomGithubRepositoriesCache(database)
    }
}