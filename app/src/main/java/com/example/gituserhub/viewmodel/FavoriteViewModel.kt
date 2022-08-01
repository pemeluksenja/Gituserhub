package com.example.gituserhub.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gituserhub.repository.FavUserRepository
import com.example.gituserhub.roomdb.FavUser

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favUserRepo: FavUserRepository = FavUserRepository(application)
    fun getTotalData() = favUserRepo.getTotalCount()
    fun getFavUserList(): LiveData<List<FavUser>> = favUserRepo.getFavUserList()
    fun deleteAll() = favUserRepo.deleteAll()
}

