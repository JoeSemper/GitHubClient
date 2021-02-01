package com.joesemper.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joesemper.githubclient.ApiHolder
import com.joesemper.githubclient.App
import com.joesemper.githubclient.R
import com.joesemper.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.entity.room.Database
import com.joesemper.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo
import com.joesemper.githubclient.mvp.presenter.UserPresenter
import com.joesemper.githubclient.mvp.view.UserView
import com.joesemper.githubclient.ui.BackButtonListener
import com.joesemper.githubclient.ui.adapter.RepositoriesRVAdapter
import com.joesemper.githubclient.ui.network.AndroidNetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class UserFragment() : MvpAppCompatFragment(), UserView, BackButtonListener {

    // ДЗ избавиться из зависимостей ниже
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var database: Database

    private lateinit var user: GithubUser

    companion object {
        private const val USER_ARG = "user"

        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }

            App.instance.appComponent.inject(this)
        }
    }

    val presenter: UserPresenter by moxyPresenter {
        user = arguments?.getParcelable<GithubUser>(USER_ARG) as GithubUser

        UserPresenter(user, AndroidSchedulers.mainThread(),
            RetrofitGithubRepositoriesRepo(ApiHolder().api, AndroidNetworkStatus(App.instance), RoomGithubRepositoriesCache(database)),
            router
        )
    }

    private var adapter: RepositoriesRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_user, null)

    override fun init() {
        tv_user_name.text = user.login
        tv_user_id.text = "id:${user.id}"

        rv_repositories.layoutManager = LinearLayoutManager(context)

        adapter = RepositoriesRVAdapter(presenter.repositoriesListPresenter)
        rv_repositories.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}