package com.joesemper.githubclient.mvp.presenter

import com.joesemper.githubclient.mvp.view.list.UserView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class UserPresenter(private val router: Router) :
    MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }



}