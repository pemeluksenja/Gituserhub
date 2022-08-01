package com.example.gituserhub.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserToFavorite(favUser: FavUser)

    @Query("SELECT * FROM fav_users ORDER BY _id ASC")
    fun getFavUserList(): LiveData<List<FavUser>>

    @Query("SELECT count(*) FROM fav_users WHERE username = :username")
    fun isUserAlreadyExist(username: String): Int

    @Query("SELECT count(*) FROM fav_users ORDER BY _id ASC")
    fun getTotalData(): Int

    @Query("DELETE FROM fav_users WHERE username = :username")
    fun deleteByUsername(username: String)

    @Query("DELETE FROM fav_users")
    fun deleteAll()
}