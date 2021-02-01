package com.joesemper.githubclient.mvp.presenter

import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.repo.IGithubUserRepositoriesRepo
import com.joesemper.githubclient.mvp.presenter.list.IRepositoryListPresenter
import com.joesemper.githubclient.mvp.view.list.RepositoryItemView
import com.joesemper.githubclient.mvp.view.UserView
import com.joesemper.githubclient.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class UserPresenter(
    private val mainThreadScheduler: Scheduler,
    private val user: GithubUser,
    private val repositoriesRepo: IGithubUserRepositoriesRepo,
    private val router: Router
) : MvpPresenter<UserView>() {


    class RepositoriesListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GithubRepository>()
        override var itemClickListener: ((RepositoryItemView) -> Unit)? = null
        override fun getCount() = repositories.size

        override fun bindView(view: RepositoryItemView) {
            val repository = repositories[view.pos]
            repository.name?.let { view.setName(it) }
        }
    }

    val repositoriesListPresenter = RepositoriesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        setClickListeners()
        loadData()
        viewState.init()
    }

    fun loadData() {
        repositoriesRepo.getUserRepositories(user)
            .observeOn(mainThreadScheduler)
            .subscribe({ repositories ->
                repositoriesListPresenter.repositories.clear()
                repositoriesListPresenter.repositories.addAll(repositories)
                viewState.updateList()
            }, {
                println("Error: ${it.message}")
            })
    }

    private fun setClickListeners() {
        repositoriesListPresenter.itemClickListener = { itemView ->
            val screen = getScreenByPosition(itemView.pos)

            router.navigateTo(screen)
        }
    }

    private fun getScreenByPosition(pos: Int): Screen {
        return Screens.RepositoryScreen(repositoriesListPresenter.repositories[pos])
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }


}