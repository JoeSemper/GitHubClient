package com.joesemper.githubclient.mvp.model.repo.retrofit

import com.joesemper.githubclient.mvp.model.api.IDataSource
import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.repo.IGithubUserRepositoriesRepo
import com.joesemper.githubclient.mvp.model.repo.IGithubUsersRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(val api: IDataSource) : IGithubUsersRepo {

    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}