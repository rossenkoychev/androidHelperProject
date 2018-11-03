package com.example.rossen.androidadvancedlearning.mainpackage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.espresso.MenuActivity
import com.example.rossen.androidadvancedlearning.fragments.ui.MainFragmentsActivity
import com.example.rossen.androidadvancedlearning.libraries.EmojifyMeActivity
import com.example.rossen.androidadvancedlearning.mvp.MVPMainActivity
import com.example.rossen.androidadvancedlearning.mvvm.MVVMMainActivity
import com.example.rossen.androidadvancedlearning.shush_me.ShushMainActivity
import com.example.rossen.androidadvancedlearning.squaker_cloud.MainSquakerActivity
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        fragments_button.setOnClickListener {
            val fragmentsIntent = Intent(this, MainFragmentsActivity::class.java)
            startActivity(fragmentsIntent)
        }

        libraries_button.setOnClickListener {
            val librariesIntent = Intent(this, EmojifyMeActivity::class.java)
            startActivity(librariesIntent)
        }

        push_messages_button.setOnClickListener {
            val push_messages_intent = Intent(this, MainSquakerActivity::class.java)
            startActivity(push_messages_intent)
        }

        places_button.setOnClickListener {
            val placesIntent = Intent(this, ShushMainActivity::class.java)
            startActivity(placesIntent)
        }

        mvp_button.setOnClickListener {
            val placesIntent = Intent(this, MVPMainActivity::class.java)
            startActivity(placesIntent)
        }

        espresso_button.setOnClickListener {
            val espressoIntent=Intent(this,MenuActivity::class.java)
            startActivity(espressoIntent)
        }

        mvvm_button.setOnClickListener {
            val mvvmIntent=Intent(this,MVVMMainActivity::class.java)
            startActivity(mvvmIntent)
        }

    }


}