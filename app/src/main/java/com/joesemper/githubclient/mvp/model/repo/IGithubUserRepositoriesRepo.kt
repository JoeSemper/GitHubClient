package com.joesemper.githubclient.mvp.model.repo

import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepositoriesRepo {
    fun getUserRepositories(url: String): Single<List<GithubRepository>>
}