package com.joesemper.githubclient.mvp.presenter

import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.repo.IGithubUsersRepo
import com.joesemper.githubclient.mvp.presenter.list.IUserListPresenter
import com.joesemper.githubclient.mvp.view.UsersView
import com.joesemper.githubclient.mvp.view.list.UserItemView
import com.joesemper.githubclient.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen


class UsersPresenter(
    private val mainThreadScheduler: Scheduler,
    private val usersRepo: IGithubUsersRepo,
    private val router: Router
) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]

            user.login?.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadAvatar(it) }
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadData()
        setClickListeners()
    }

    private fun loadData() {
        usersRepo.getUsers()
            .observeOn(mainThreadScheduler)
            .subscribe({ users ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(users)
                viewState.updateList()
            }, {
                println("Error: ${it.message}")
            })
    }

    private fun setClickListeners() {
        usersListPresenter.itemClickListener = { itemView ->
            val screen = getScreenByPosition(itemView.pos)

            router.navigateTo(screen)
        }
    }

    private fun getScreenByPosition(pos: Int): Screen {
        return Screens.UserScreen(usersListPresenter.users[pos])
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}