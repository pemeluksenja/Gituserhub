package com.example.gituserhub.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gituserhub.viewmodel.FavoriteViewModel
import com.example.gituserhub.viewmodel.UserDetailViewModel

class DetailViewModelFactory(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: DetailViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): DetailViewModelFactory {
            if (INSTANCE == null) {
                synchronized(DetailViewModelFactory::class.java) {
                    INSTANCE = DetailViewModelFactory(application)
                }
            }
            return INSTANCE as DetailViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}