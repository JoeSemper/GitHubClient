package com.joesemper.githubclient.navigation

import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.ui.fragments.RepositoryFragment
import com.joesemper.githubclient.ui.fragments.UserFragment
import com.joesemper.githubclient.ui.fragments.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen() : SupportAppScreen() {
        override fun getFragment() = UsersFragment.newInstance()
    }
    class UserScreen(private val user: GithubUser) : SupportAppScreen() {
        override fun getFragment() = UserFragment.newInstance(user)
    }

    class RepositoryScreen(val user: GithubRepository) : SupportAppScreen() {
        override fun getFragment() = RepositoryFragment.newInstance(user)
    }
}