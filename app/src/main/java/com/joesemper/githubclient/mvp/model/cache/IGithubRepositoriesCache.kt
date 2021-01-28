package com.joesemper.githubclient.mvp.model.cache

import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesCache {
    fun cacheRepositories(user: GithubUser, repositories: List<GithubRepository>)
    fun getRepositories(user: GithubUser): Single<List<GithubRepository>>
}