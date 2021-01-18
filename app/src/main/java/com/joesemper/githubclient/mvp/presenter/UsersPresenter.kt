package com.joesemper.githubclient.mvp.presenter

import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.entity.GithubUsersRepo
import com.joesemper.githubclient.mvp.presenter.list.IUserListPresenter
import com.joesemper.githubclient.mvp.view.UsersView
import com.joesemper.githubclient.mvp.view.list.UserItemView
import com.joesemper.githubclient.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = {itemView ->
            val pos = itemView.pos
            val screen = getScreenByPosition(pos)

            router.navigateTo(screen)
        }
    }

    private fun getScreenByPosition(pos: Int): Screen {
        val users = usersRepo.getUsers()
        return Screens.UserScreen(users[pos])
    }

    private fun loadData() {
        val users =  usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}