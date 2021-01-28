package com.joesemper.githubclient.mvp.model.repo.retrofit

import com.joesemper.githubclient.mvp.model.api.IDataSource
import com.joesemper.githubclient.mvp.model.cache.IGithubUsersCache
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.network.INetworkStatus
import com.joesemper.githubclient.mvp.model.repo.IGithubUsersRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IGithubUsersCache
) : IGithubUsersRepo {

    override fun getUsers() = checkOnlineStatus().flatMap { isOnline ->
        if (isOnline) {
            getUsersFromNetwork().flatMap { users ->
                cacheUsers(users)
                createSingle(users)
            }
        } else {
            getUsersFromCache()
        }
    }.subscribeOn(Schedulers.io())


    private fun checkOnlineStatus(): Single<Boolean> = networkStatus.isOnlineSingle()

    private fun getUsersFromNetwork(): Single<List<GithubUser>> = api.getUsers()

    private fun getUsersFromCache(): Single<List<GithubUser>> = cache.getUsers()

    private fun cacheUsers(users: List<GithubUser>) = cache.cacheUsers(users)

    private fun createSingle(data: List<GithubUser>) = Single.fromCallable { data }
}