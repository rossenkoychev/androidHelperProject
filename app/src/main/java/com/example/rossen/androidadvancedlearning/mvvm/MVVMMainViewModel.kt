package com.example.rossen.androidadvancedlearning.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import com.example.rossen.androidadvancedlearning.users.usersdb.User
import com.example.rossen.androidadvancedlearning.users.usersdb.UserDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MVVMMainViewModel(val context: Application) : AndroidViewModel(context) {

    var disposable: CompositeDisposable = CompositeDisposable()
    var allNames: MutableLiveData<List<User>> = MutableLiveData()
    var user: User? = null
    val db = UserDB.getInstance(context)

    //fun loadUsers() {
    init {
        disposable.add(db.userDao().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            allNames.value = it
                        },
                        {
                            //do nothing
                        })
        )
    }
//        allNames = LiveDataReactiveStreams.fromPublisher(
//                db.userDao().getUsers()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread()))

    fun addUser(userName: String) {
//        disposable.add(db.userDao().insertUser(User(userName))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({loadUsers() }, { _ ->
//                    //throw error
//                })
        val insertRunnable = Runnable {
            db.userDao().insertUser(User(userName))
        }
        val insertThread = Thread(insertRunnable)
        insertThread.start()
    }
}