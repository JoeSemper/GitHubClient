package com.joesemper.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joesemper.githubclient.App
import com.joesemper.githubclient.R
import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.presenter.RepositoryPresenter
import com.joesemper.githubclient.mvp.view.RepositoryView
import com.joesemper.githubclient.ui.BackButtonListener
import com.joesemper.githubclient.ui.adapter.RepositoriesRVAdapter
import kotlinx.android.synthetic.main.fragment_repository.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {

    // ДЗ Избавиться от инъекции ниже
    @Inject
    lateinit var router: Router

    companion object {
        private const val REPOSITORY_ARG = "repository"

        fun newInstance(repository: GithubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_ARG, repository)
            }

            App.instance.appComponent.inject(this)
        }
    }

    var adapter: RepositoriesRVAdapter? = null

    val presenter: RepositoryPresenter by moxyPresenter {
        val repository = arguments?.getParcelable<GithubRepository>(REPOSITORY_ARG) as GithubRepository

        RepositoryPresenter(repository, router)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_repository, null)

    override fun init() {}

    override fun setId(text: String) {
        tv_repository_id.text = text
    }

    override fun setTitle(text: String) {
        tv_repository_title.text = text
    }

    override fun setForksCount(text: String) {
        tv_forksCount.text = text
    }

    override fun backPressed() = presenter.backPressed()

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("onDestroy")
    }
}