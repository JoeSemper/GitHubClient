package com.joesemper.githubclient.di.modules

import com.joesemper.githubclient.mvp.model.api.IDataSource
import com.joesemper.githubclient.mvp.model.cache.IGithubRepositoriesCache
import com.joesemper.githubclient.mvp.model.cache.IGithubUsersCache
import com.joesemper.githubclient.mvp.model.network.INetworkStatus
import com.joesemper.githubclient.mvp.model.repo.IGithubUserRepositoriesRepo
import com.joesemper.githubclient.mvp.model.repo.IGithubUsersRepo
import com.joesemper.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo
import com.joesemper.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun usersRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IGithubUsersCache) : IGithubUsersRepo =
        RetrofitGithubUsersRepo(api, networkStatus, cache)

    @Singleton
    @Provides
    fun repositoriesRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IGithubRepositoriesCache): IGithubUserRepositoriesRepo =
        RetrofitGithubRepositoriesRepo(api, networkStatus, cache)
}