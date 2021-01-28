package com.joesemper.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joesemper.githubclient.App
import com.joesemper.githubclient.R
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.presenter.UserPresenter
import com.joesemper.githubclient.mvp.view.list.UserView
import com.joesemper.githubclient.ui.BackButtonListener
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment(private val user: GithubUser) : MvpAppCompatFragment(), UserView, BackButtonListener {

    companion object {
        fun newInstance(user: GithubUser) = UserFragment(user)
    }

    private val presenter : UserPresenter by moxyPresenter { UserPresenter(App.instance.router) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_user, null)

    override fun init() {
        tv_user_name.text = user.login
    }

    override fun backPressed()  = presenter.backPressed()
}