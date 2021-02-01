package com.joesemper.githubclient.mvp.model.cache.room

import com.joesemper.githubclient.mvp.model.entity.room.Database
import com.joesemper.githubclient.mvp.model.cache.IGithubRepositoriesCache
import com.joesemper.githubclient.mvp.model.entity.GithubRepository
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.entity.room.RoomGithubRepository
import com.joesemper.githubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Single

class RoomGithubRepositoriesCache(private val db: Database) : IGithubRepositoriesCache {

    override fun cacheRepositories(user: GithubUser, repositories: List<GithubRepository>) {
        val roomRepository = createRoomUserRepositoriesRepo(user, repositories)
        insertRepositoriesToDb(roomRepository)
    }

    override fun getRepositories(user: GithubUser) = Single.fromCallable {
        val roomUser = createRoomUser(user)
        getUserRepositoriesFromDb(roomUser)
    }


    private fun createRoomUserRepositoriesRepo(user: GithubUser, repositories: List<GithubRepository>
    ): List<RoomGithubRepository> {
        val roomUser = createRoomUser(user)
        return repositories.map {
            RoomGithubRepository(
                it.id ?: "",
                it.name ?: "",
                it.forksCount ?: 0,
                roomUser.id
            )
        }
    }

    private fun createRoomUser(user: GithubUser) = user.login.let { db.userDao.findByLogin(it) }

    private fun insertRepositoriesToDb(roomRepositories: List<RoomGithubRepository>) =
        db.repositoryDao.insert(roomRepositories)

    private fun getUserRepositoriesFromDb(roomUser: RoomGithubUser) =
        db.repositoryDao.findForUser(roomUser.id)
            .map { GithubRepository(it.id, it.name, it.forksCount) }

}
