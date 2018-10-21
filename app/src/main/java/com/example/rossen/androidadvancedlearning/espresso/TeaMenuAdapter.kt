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

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.espresso.model.Tea
import java.util.*

/**
 * TeaMenuAdapter is backed by an ArrayList of [Tea] objects which populate
 * the GridView in MenuActivity
 */

class TeaMenuAdapter(private val mContext: Context, private val layoutResourceId: Int,val data: ArrayList<Tea>) :
ArrayAdapter<Tea>(mContext, layoutResourceId, data) {


    internal class ViewHolder {
        var imageTitle: TextView? = null
        var image: ImageView? = null
    }

    override// Create a new ImageView for each item referenced by the Adapter
    fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        var holder: ViewHolder? = null
        val currentTea = getItem(position)

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(layoutResourceId, parent, false)
            holder = ViewHolder()
            holder.imageTitle = convertView!!.findViewById<View>(R.id.tea_grid_name) as TextView
            holder.image = convertView.findViewById<View>(R.id.image) as ImageView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.imageTitle!!.setText(currentTea.teaName)
        holder.image!!.setImageResource(currentTea.imageResourceId)
        return convertView
    }

}