package com.example.rossen.androidadvancedlearning.users.usersdb

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDB? = null

//        fun getInstance(context: Context): UserDB? {
//            if (INSTANCE == null) {
//                synchronized(UserDB::class) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            UserDB::class.java, "UserDB.db")
//                            .build()
//                }
//            }
//            return INSTANCE
//        }

        fun getInstance(context: Context): UserDB =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        UserDB::class.java, "UserDB.db")
                        .build()


        fun destroyInstance() {
            INSTANCE = null
        }
    }
}