package com.example.gituserhub.model

import com.google.gson.annotations.SerializedName

data class UserListModel(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("node_id")
	val nodeId: String
)
