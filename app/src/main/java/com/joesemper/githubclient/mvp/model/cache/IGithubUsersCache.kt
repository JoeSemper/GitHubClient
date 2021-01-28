package com.joesemper.githubclient.mvp.model.cache

import com.joesemper.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUsersCache {

    fun cacheUsers(users: List<GithubUser>)
    fun getUsers(): Single<List<GithubUser>>
}