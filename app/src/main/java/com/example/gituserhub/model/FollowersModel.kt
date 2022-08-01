package com.example.gituserhub.model

import com.google.gson.annotations.SerializedName

data class FollowersModelItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("id")
	val id: Int,
)
