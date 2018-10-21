package com.example.rossen.androidadvancedlearning.mvp

import com.example.rossen.androidadvancedlearning.users.usersdb.User

class MainPresenter(val mainView: MainContract.View) : MainContract.Presenter {


    var playerNumber = 0

    override fun addUser(name: String) {
        val user = User(name)
        playerNumber++
        mainView.announceUser(name)
        mainView.updateUserCount(playerNumber)

    }
}