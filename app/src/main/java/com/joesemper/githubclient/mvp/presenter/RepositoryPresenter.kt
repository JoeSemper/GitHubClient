package com.joesemper.githubclient.mvp.presenter

import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.view.RepositoryView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class RepositoryPresenter(val githubRepository: GithubRepository, val router: Router) : MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(githubRepository.id ?: "")
        viewState.setTitle(githubRepository.name ?: "")
        viewState.setForksCount((githubRepository.forksCount ?: 0).toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("onDestroy presenter")
    }
}