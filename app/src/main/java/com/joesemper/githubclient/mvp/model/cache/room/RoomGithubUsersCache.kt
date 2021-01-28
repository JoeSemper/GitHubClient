package com.joesemper.githubclient.mvp.model.cache.room

import com.joesemper.githubclient.mvp.model.cache.IGithubUsersCache
import com.joesemper.githubclient.mvp.model.entity.GithubUser
import com.joesemper.githubclient.mvp.model.entity.room.Database
import com.joesemper.githubclient.mvp.model.entity.room.RoomGithubUser
import io.reactivex.rxjava3.core.Single

class RoomGithubUsersCache(private val db: Database) : IGithubUsersCache {

    override fun cacheUsers(users: List<GithubUser>) {
        val roomUsers = createRoomGithubUsers(users)
        insertUsersToDb(roomUsers)
    }

    override fun getUsers() = Single.fromCallable { getGithubUsersFromDb() }


    private fun createRoomGithubUsers(users: List<GithubUser>) = users.map { user ->
        RoomGithubUser(
            user.id ?: "",
            user.login ?: "",
            user.avatarUrl ?: "",
            user.reposUrl ?: ""
        )
    }

    private fun getGithubUsersFromDb() = db.userDao.getAll().map { roomUser ->
        GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
    }

    private fun insertUsersToDb(roomUsers: List<RoomGithubUser>) = db.userDao.insert(roomUsers)
}