package com.example.rossen.androidadvancedlearning.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.androidadvancedlearning.users.usersdb.User
import com.example.rossen.androidadvancedlearning.users.usersdb.UserDB
import io.reactivex.disposables.CompositeDisposable

class MVVMMainViewModel(val context: Application) : AndroidViewModel(context){

    var disposable:CompositeDisposable=CompositeDisposable()
    val name : MutableLiveData<String> = MutableLiveData()

    fun getNames(): LiveData<List<User>>? {
       // var users:MutableLiveData<String>
       return UserDB.getInstance(context)?.userDao()?.getUsers()
    }
}