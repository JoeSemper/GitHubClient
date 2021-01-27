package com.joesemper.githubclient.mvp.model.repo.retrofit

import com.joesemper.githubclient.mvp.model.api.IDataSource
import com.joesemper.githubclient.mvp.model.repo.IGithubUserRepositoriesRepo
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(val api: IDataSource): IGithubUserRepositoriesRepo {

    override fun getUserRepositories(url: String) = api
        .getUserRepositories(url)
        .subscribeOn(Schedulers.io())
}