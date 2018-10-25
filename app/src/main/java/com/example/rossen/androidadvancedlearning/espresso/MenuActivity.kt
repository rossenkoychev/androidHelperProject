/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.rossen.androidadvancedlearning.espresso

import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.GridView

import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.espresso.model.Tea

import java.util.ArrayList

class MenuActivity : AppCompatActivity(),ImageDownloader.DelayerCallback {

    private var mIdlingResource: SimpleIdlingResource? = null

    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val menuToolbar = findViewById<View>(R.id.menu_toolbar) as Toolbar
        //  setSupportActionBar(menuToolbar)
        supportActionBar!!.setTitle(getString(R.string.menu_title))

        getIdlingResource()
    }

    override fun onStart() {
        super.onStart()
        ImageDownloader.downloadImage(this,this , mIdlingResource)
    }


    override fun onDone( teas:ArrayList<Tea> )
    {
        // Create an ArrayList of teas
        val teas = ArrayList<Tea>()
        teas.add(Tea(getString(R.string.black_tea_name), R.drawable.black_tea))
        teas.add(Tea(getString(R.string.green_tea_name), R.drawable.green_tea))
        teas.add(Tea(getString(R.string.white_tea_name), R.drawable.white_tea))
        teas.add(Tea(getString(R.string.oolong_tea_name), R.drawable.oolong_tea))
        teas.add(Tea(getString(R.string.honey_lemon_tea_name), R.drawable.honey_lemon_tea))
        teas.add(Tea(getString(R.string.chamomile_tea_name), R.drawable.chamomile_tea))

        // Create a {@link TeaAdapter}, whose data source is a list of {@link Tea}s.
        // The adapter know how to create grid items for each item in the list.
        val gridview = findViewById<View>(R.id.tea_grid_view) as GridView
        val adapter = TeaMenuAdapter(this, R.layout.grid_item_layout, teas)
        gridview.adapter = adapter


        // Set a click listener on that View
        gridview.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Tea
            // Set the intent to open the {@link OrderActivity}
            val mTeaIntent = Intent(this@MenuActivity, OrderActivity::class.java)
            val teaName = item.teaName
            mTeaIntent.putExtra(EXTRA_TEA_NAME, teaName)
            startActivity(mTeaIntent)
        }
    }

    companion object {
        val EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME"
    }

}