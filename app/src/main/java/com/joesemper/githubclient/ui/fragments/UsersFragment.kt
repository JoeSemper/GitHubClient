package com.joesemper.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.githubclient.App
import com.joesemper.githubclient.R
import com.joesemper.githubclient.mvp.presenter.UsersPresenter
import com.joesemper.githubclient.mvp.view.UsersView
import com.joesemper.githubclient.ui.BackButtonListener
import com.joesemper.githubclient.ui.adapter.UsersRVAdapter
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var adapter: UsersRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        View.inflate(context, R.layout.fragment_users, null)

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(context)

        adapter = UsersRVAdapter(presenter.usersListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        rv_users.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}