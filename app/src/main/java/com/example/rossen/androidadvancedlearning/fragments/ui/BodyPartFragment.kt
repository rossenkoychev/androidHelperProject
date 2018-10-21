package com.example.rossen.androidadvancedlearning.fragments.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.rossen.androidadvancedlearning.R
import java.util.ArrayList

class BodyPartFragment() : Fragment() {

    companion object {
        private val TAG = BodyPartFragment::class.qualifiedName
        private const val IMAGE_ID_LIST="image ids"
        private const val LIST_INDEX="list index"
    }

    var imageIds:List<Int> = listOf()
    var listIndex:Int=0
     lateinit var imageView:ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(savedInstanceState!=null){
            imageIds=savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST)
            listIndex=savedInstanceState.getInt(LIST_INDEX)
        }
        val rootView = inflater.inflate(R.layout.fragment_body_part, container, false)

        imageView = rootView.findViewById(R.id.body_part_imageView) as ImageView
        if(imageIds.isEmpty()){
            Log.v(TAG,"This fragment has empty list of images")
        }else{
            imageView.setImageResource(imageIds.get(listIndex))
        }

        imageView.setOnClickListener {
            if(listIndex<imageIds.size-1) listIndex++ else listIndex=0
        imageView.setImageResource(imageIds.get(listIndex))}

        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(IMAGE_ID_LIST,ArrayList<Int>(imageIds))
        outState.putInt(LIST_INDEX,listIndex)
        super.onSaveInstanceState(outState)
    }

    fun updateView(){
        imageView.setImageResource(imageIds.get(listIndex))
    }
}