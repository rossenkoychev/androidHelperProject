package com.example.rossen.androidadvancedlearning.mvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.users.usersdb.User
import com.example.rossen.androidadvancedlearning.utils.KeyboardUtils
import kotlinx.android.synthetic.main.mvp_main_activity_layout.*


class MVVMMainActivity : AppCompatActivity() {

    lateinit var viewModel: MVVMMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvvm_main_activity_layout)
        viewModel = ViewModelProviders.of(this).get(MVVMMainViewModel::class.java)

        val usersObserver = Observer<List<User>> { users ->
            playersCountValueTextView.text = users?.size.toString()
            val iterator = users?.iterator()
            val userss: StringBuilder = StringBuilder()
            iterator?.forEach { userss.append(" \n Player ${it.userName} has joined the party") }
            usersJoinedTextView.text = userss.toString()

        }
        viewModel.allNames.observe(this, usersObserver)

        addListeners()

        // viewModel.loadUsers()

        playerEditText.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) KeyboardUtils.hideKeyboard(view, this)
        }
    }

    fun addListeners() {
        addUserButton.setOnClickListener {
            KeyboardUtils.hideKeyboard(it, this)
            viewModel.addUser(playerEditText.text.toString())
            playerEditText.text.clear()
        }
    }


}