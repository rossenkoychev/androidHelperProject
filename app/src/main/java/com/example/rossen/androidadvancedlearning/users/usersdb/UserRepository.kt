package com.example.rossen.androidadvancedlearning.users.usersdb

import android.app.Application
import android.arch.lifecycle.LiveData
import io.reactivex.Flowable


class UserRepository(var application:Application) {

    private var userDao: UserDao? = null
    private var allUsers: Flowable<List<User>>? = null

    init{
        val db = UserDB.getInstance(application)
      //  userDao = db.userDao()
        allUsers = db.userDao().getUsers()
    }


}