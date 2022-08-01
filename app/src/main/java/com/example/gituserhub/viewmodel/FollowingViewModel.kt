package com.example.gituserhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserhub.APIUtils.APIConfig
import com.example.gituserhub.model.FollowersModelItem
import com.example.gituserhub.model.UserProfiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val followingList = MutableLiveData<ArrayList<UserProfiles>>()
    val list = ArrayList<UserProfiles>()
    fun getFollowingList(name: String) {
        val client = APIConfig.getAPIServices().getFollowingUsn(name)
        client.enqueue(object : Callback<List<FollowersModelItem>> {
            override fun onResponse(
                call: Call<List<FollowersModelItem>>,
                response: Response<List<FollowersModelItem>>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    Log.d("RESBODY", resBody.toString())
                    if (resBody != null) {
                        for (item in resBody) {
                            setDataFollowing(item)
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowersModelItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setDataFollowing(following: FollowersModelItem) {
        if (following !== null) {
            val names = following.login.uppercase()
            val ava = following.avatarUrl
            val username = following.login
            val company = "PT Jaya Raya"
            val location = "Indonesia"
            val followersCount = (1..100).random()
            val following = (1..100).random()
            val repository = (1..100).random()

            val addUser = UserProfiles(
                username,
                names,
                ava,
                location,
                company,
                followersCount,
                following,
                repository,
                false
            )
            list.add(addUser)
        }
        followingList.postValue(list)
    }

    fun getProfileFollowing(): LiveData<ArrayList<UserProfiles>> {
        return followingList
    }

    companion object {
        private const val TAG = "FollowingViewModel"
    }
}