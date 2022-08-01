package com.example.gituserhub.viewmodel
import android.util.Log
import androidx.lifecycle.*
import com.example.gituserhub.APIUtils.APIConfig
import com.example.gituserhub.model.ItemsItem
import com.example.gituserhub.model.UserListModel
import com.example.gituserhub.model.UserProfiles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val userList = MutableLiveData<ArrayList<UserProfiles>>()
    fun getUserList(name: String) {
        val list = ArrayList<UserProfiles>()
        val client = APIConfig.getAPIServices().getSearchUsn(name)
        client.enqueue(object : Callback<UserListModel> {
            override fun onResponse(
                call: Call<UserListModel>,
                response: Response<UserListModel>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    if (resBody !== null) {
                        val items = resBody.items
                        for (user in items) {
                            Log.d("APIREQUEST", user.avatarUrl)
                            list.add(setProfile(user))
                        }
                        userList.postValue(list)
                    }
                }
            }
            override fun onFailure(call: Call<UserListModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setProfile(user: ItemsItem): UserProfiles {
        val names = user.login.uppercase()
        val ava = user.avatarUrl
        val username = user.login
        val company = "PT JAYA RAYA"
        val location = "INDONESIA"
        val followers = (1..100).random()
        val following = (1..100).random()
        val repository = (1..100).random()

        val addUser = UserProfiles(
            username,
            names,
            ava,
            location,
            company,
            followers,
            following,
            repository,
            false
        )
        return addUser
    }

    fun getProfile(): LiveData<ArrayList<UserProfiles>> {
        return userList
    }

    companion object {
        private val TAG = "MainViewModel"
    }
}