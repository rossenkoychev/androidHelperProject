package com.example.rossen.androidadvancedlearning.mvp

interface MainContract {

    interface View {
        fun announceUser(name:String)
        fun updateUserCount(number:Int)
    }

    interface Presenter{
        fun addUser(name:String)
    }
}