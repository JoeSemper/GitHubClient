package com.joesemper.githubclient.di

import com.joesemper.githubclient.di.modules.*
import com.joesemper.githubclient.mvp.presenter.MainPresenter
import com.joesemper.githubclient.mvp.presenter.UsersPresenter
import com.joesemper.githubclient.ui.MainActivity
import com.joesemper.githubclient.ui.fragments.RepositoryFragment
import com.joesemper.githubclient.ui.fragments.UserFragment
import com.joesemper.githubclient.ui.fragments.UsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        DatabaseModule::class,
        CiceroneModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)

    // ДЗ - избавиться от зависимостей ниже
    fun inject(usersFragment: UsersFragment)
    fun inject(userFragment: UserFragment)
    fun inject(repositoryFragment: RepositoryFragment)
}