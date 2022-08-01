package com.example.gituserhub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.gituserhub.roomdb.FavUser
import com.example.gituserhub.roomdb.FavUserDao
import com.example.gituserhub.roomdb.FavUserDb
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val favUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = FavUserDb.getDb(application)
        favUserDao = db.favUserDao()
    }
    fun getTotalCount() = favUserDao.getTotalData()
    fun getFavUserList(): LiveData<List<FavUser>> = favUserDao.getFavUserList()
    fun addUserToFavorite(favUser: FavUser) = executorService.execute{favUserDao.addUserToFavorite(favUser)}
    fun isUserAlreadyExist(username: String):Int = favUserDao.isUserAlreadyExist(username)
    fun deleteAll() = favUserDao.deleteAll()
    fun deleteByUsernme(username: String) = favUserDao.deleteByUsername(username)



}