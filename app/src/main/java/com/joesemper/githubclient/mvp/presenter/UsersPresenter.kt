package com.joesemper.githubclient.mvp.presenter

import android.util.Log
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.entity.GithubUsersRepo
import com.joesemper.githubclient.mvp.presenter.list.IUserListPresenter
import com.joesemper.githubclient.mvp.view.UsersView
import com.joesemper.githubclient.mvp.view.list.UserItemView
import com.joesemper.githubclient.navigation.Screens
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

const val TAG = "RxJava"

class UsersPresenter(private val usersRepo: GithubUsersRepo, private val router: Router) :
    MvpPresenter<UsersView>() {

    private lateinit var users: List<GithubUser>

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
        subscribeForDb()
        loadData()
        setClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeForDb()
    }

    private fun subscribeForDb() {
        usersRepo.getUsers().subscribe(userObserver)
    }

    private fun unsubscribeForDb(){
        userObserver.disposable?.dispose()
    }

    private fun loadData() {
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    private fun setClickListeners() {
        usersListPresenter.itemClickListener = { itemView ->
            val screen = getScreenByPosition(itemView.pos)
            router.navigateTo(screen)
        }
    }

    private fun getScreenByPosition(pos: Int): Screen {
        return Screens.UserScreen(users[pos])
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    private val userObserver = object : Observer<List<GithubUser>> {
        var disposable: Disposable? = null

        override fun onSubscribe(d: Disposable?) {
            disposable = d
        }

        override fun onNext(usersList: List<GithubUser>) {
            users = usersList
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError: ${e?.message}")
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete")
        }
    }
}