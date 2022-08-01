package com.example.gituserhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfiles(
    val names: String,
    val usernames: String,
    val pfp: String,
    val userLocs: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val repository: Int,
    val isFavUser: Boolean,
    ) : Parcelable
