package com.example.rossen.androidadvancedlearning.fragments.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.fragments.data.AndroidImageAssets

class MasterListFragment : Fragment() {

    lateinit var callBack:OnImageClickListener

    interface OnImageClickListener {
        fun onImageSelected(position:Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try{
            callBack=context as OnImageClickListener
        }catch ( e:ClassCastException){
            throw ClassCastException(context.toString() + "must implement OnImageClickListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_master_list, container, false)

        val adapter=MasterListAdapter(context!!,AndroidImageAssets.all)
        val gridView: GridView = rootView.findViewById<GridView>(R.id.master_grid_view)
        gridView.adapter=adapter

        gridView.setOnItemClickListener { parent, view, position, id ->  callBack.onImageSelected(position)}
        return rootView
    }
}