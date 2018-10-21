package com.example.rossen.androidadvancedlearning.shush_me

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rossen.androidadvancedlearning.R
import kotlinx.android.synthetic.main.item_place_card.view.*

class PlaceListAdapter(val context:Context) : RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>(){

    var items=listOf("positoon1","position2")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        // Get the RecyclerView item layout
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_place_card, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return 0
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTextView: TextView
        var addressTextView: TextView

        init {
            nameTextView = itemView.name_text_view
            addressTextView = itemView.address_text_view
        }
        fun bind(item:String){
            with(itemView){
                //bind directly name_text_view with list[position].name
            }
        }

    }
}
