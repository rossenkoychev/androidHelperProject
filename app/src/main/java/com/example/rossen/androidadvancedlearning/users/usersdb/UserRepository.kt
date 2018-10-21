package com.example.rossen.androidadvancedlearning.users.usersdb

import android.app.Application
import android.arch.lifecycle.LiveData



class UserRepository(var application:Application) {

    private var userDao: UserDao? = null
    private var allUsers: LiveData<List<User>>? = null

    init{
        val db = UserDB.getInstance(application)
        userDao = db?.userDao()
        allUsers = userDao?.getUsers()
    }


}