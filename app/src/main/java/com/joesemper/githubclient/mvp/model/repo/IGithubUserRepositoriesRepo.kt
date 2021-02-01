package com.joesemper.githubclient.mvp.model.repo

import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepositoriesRepo {
    fun getUserRepositories(user: GithubUser): Single<List<GithubRepository>>
}