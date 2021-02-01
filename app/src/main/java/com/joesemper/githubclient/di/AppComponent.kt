package com.joesemper.githubclient.di

import com.joesemper.githubclient.di.modules.*
import com.joesemper.githubclient.mvp.presenter.MainPresenter
import com.joesemper.githubclient.mvp.presenter.RepositoryPresenter
import com.joesemper.githubclient.mvp.presenter.UserPresenter
import com.joesemper.githubclient.mvp.presenter.UsersPresenter
import com.joesemper.githubclient.ui.MainActivity
import com.joesemper.githubclient.ui.adapter.UsersRVAdapter
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
        RepoModule::class,
        ImageLoaderModule::class,
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)
}