package com.joesemper.githubclient.mvp.model.repo.retrofit

import com.joesemper.githubclient.mvp.model.api.IDataSource
import com.joesemper.githubclient.mvp.model.cache.IGithubRepositoriesCache
import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.entity.room.Database
import com.joesemper.githubclient.mvp.model.entity.room.RoomGithubRepository
import com.joesemper.githubclient.mvp.model.network.INetworkStatus
import com.joesemper.githubclient.mvp.model.repo.IGithubUserRepositoriesRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: IGithubRepositoriesCache
) :
    IGithubUserRepositoriesRepo {

    override fun getUserRepositories(user: GithubUser) =
        checkOnlineStatus().flatMap { isOnline ->
            if (isOnline) {
                getUserRepositoriesFromNetwork(user)?.flatMap { repositories ->
                    cacheUserRepositories(user, repositories)
                    createSingle(repositories)
                }
            } else {
                getUserRepositoriesFromCache(user)
            }
        }.subscribeOn(Schedulers.io())



    private fun checkOnlineStatus(): Single<Boolean> = networkStatus.isOnlineSingle()

    private fun getUserRepositoriesFromNetwork(user: GithubUser) =
        user.reposUrl?.let { url ->
            api.getUserRepositories(url)
        }

    private fun cacheUserRepositories(user: GithubUser, repositories: List<GithubRepository>) =
        cache.cacheRepositories(user, repositories)

    private fun getUserRepositoriesFromCache(user: GithubUser) = cache.getRepositories(user)

    private fun createSingle(data: List<GithubRepository>) = Single.fromCallable { data }
}