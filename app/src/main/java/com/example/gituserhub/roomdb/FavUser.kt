package com.example.gituserhub.roomdb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_users")
@Parcelize
data class FavUser (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "_id")
    var _id:Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,
) : Parcelable