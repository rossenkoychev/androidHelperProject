package com.example.rossen.androidadvancedlearning.fragments.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class MasterListAdapter(private val context: Context,private val imageIds: List<Int>) : BaseAdapter() {


    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imageIds.size
    }

    override fun getView(position: Int, convertView: View?, parentView: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(context)
            imageView.adjustViewBounds=true
            imageView.scaleType=ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else imageView = convertView as ImageView

        imageView.setImageResource(imageIds.get(position))
        return imageView
    }

}