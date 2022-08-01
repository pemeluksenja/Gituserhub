package com.example.gituserhub.APIUtils


import com.example.gituserhub.BuildConfig
import com.example.gituserhub.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIServices {
    @GET("users/{username}")
    @Headers("Authorization: token $githubToken")
    fun getLoginUsn(
        @Path("username") username: String
    ): Call<UserDetailModel>

    @GET("search/users")
    @Headers("Authorization: token $githubToken")
    fun getSearchUsn(
        @Query("q") q: String
    ): Call<UserListModel>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $githubToken")
    fun getFollowersUsn(
        @Path("username") username: String
    ): Call<List<FollowersModelItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $githubToken")
    fun getFollowingUsn(
        @Path("username") username: String
    ): Call<List<FollowersModelItem>>

    companion object {
        const val githubToken = BuildConfig.KEY
    }
}