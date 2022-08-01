package com.example.gituserhub.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUser::class], version = 1)
abstract class FavUserDb : RoomDatabase() {
    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavUserDb? = null

        @JvmStatic
        fun getDb(context: Context): FavUserDb {
            if (INSTANCE == null) {
                synchronized(FavUserDb::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserDb::class.java,
                        "fav_user"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as FavUserDb
        }
    }
}