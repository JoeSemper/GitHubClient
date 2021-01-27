package com.joesemper.githubclient.mvp.model.repo

import com.joesemper.githubclient.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}