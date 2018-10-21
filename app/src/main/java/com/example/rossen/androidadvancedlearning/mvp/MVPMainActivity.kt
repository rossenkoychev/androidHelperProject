package com.example.rossen.androidadvancedlearning.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.utils.KeyboardUtils
import kotlinx.android.synthetic.main.mvp_main_activity_layout.*


class MVPMainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_main_activity_layout)
        presenter = MainPresenter(this)

        addUserButton.setOnClickListener {
            presenter.addUser(playerEditText.text.toString())
            KeyboardUtils.hideKeyboard(it,this)
        }

        playerEditText.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus) KeyboardUtils.hideKeyboard(view,this)
        }
    }

    override fun announceUser(name: String) {
        val text = usersJoinedTextView.text.toString()
        usersJoinedTextView.text = text + " \n Player ${name} has joined the party"
    }

    override fun updateUserCount(number: Int) {
        playersCountValueTextView.text = number.toString()
    }
}