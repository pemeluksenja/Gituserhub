package com.example.gituserhub.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserhub.APIUtils.APIConfig
import com.example.gituserhub.model.UserDetailModel
import com.example.gituserhub.model.UserProfiles
import com.example.gituserhub.repository.FavUserRepository
import com.example.gituserhub.roomdb.FavUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {
    var userDetail = MutableLiveData<UserProfiles>()
    private val favUserRepository: FavUserRepository = FavUserRepository(application)
    fun getUserProfile(name: String) {

        val client = APIConfig.getAPIServices().getLoginUsn(name)
        client.enqueue(object : Callback<UserDetailModel> {
            override fun onResponse(
                call: Call<UserDetailModel>,
                response: Response<UserDetailModel>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()!!
                    setUserProfile(resBody)
                    Log.d(TAG, resBody.toString())
                } else {

                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setUserProfile(user: UserDetailModel) {
        val username = if (user.login !== null) user.login else "Fulan"
        val name = if (user.name !== null) user.name.uppercase() else "ismithoriq"
        val avatar = user.avatarUrl!!
        val userLocation = if (user.location !== null) user.location else "Indonesia"
        val userCompany = if (user.company !== null) user.company else "PT Jaya Raya"
        val followers = if (user.followers !== null) user.followers else 0
        val following = if (user.following !== null) user.following else 0
        val repository = if (user.publicRepos !== null) user.publicRepos else 0

        val detail = UserProfiles(
            name, username, avatar, userLocation, userCompany, followers, following, repository, false
        )
        userDetail.postValue(detail)

    }

    fun getUsers(): MutableLiveData<UserProfiles> = userDetail
    fun addUserToFav(favUser: FavUser) = favUserRepository.addUserToFavorite(favUser)
    fun isUserAlreadyExist(username: String):Int = favUserRepository.isUserAlreadyExist(username)
    fun deleteByUsername(username: String) = favUserRepository.deleteByUsernme(username)
    companion object {
        private val TAG = "USERDETAILACTIVITY"
    }


}