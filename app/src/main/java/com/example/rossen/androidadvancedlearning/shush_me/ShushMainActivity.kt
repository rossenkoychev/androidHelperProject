package com.example.rossen.androidadvancedlearning.shush_me

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.rossen.androidadvancedlearning.R
import kotlinx.android.synthetic.main.activity_main_shushme.*

class ShushMainActivity : AppCompatActivity() {

    val TAG = ShushMainActivity::class.java.simpleName
    lateinit var placeListAdapter: PlaceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_shushme)

        places_list_recycler_view.layoutManager = (LinearLayoutManager(this))
        placeListAdapter = PlaceListAdapter(this)
        places_list_recycler_view.adapter = placeListAdapter
    }
}