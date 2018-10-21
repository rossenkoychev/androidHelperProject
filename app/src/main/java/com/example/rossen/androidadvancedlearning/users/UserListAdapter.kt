package com.example.rossen.androidadvancedlearning.users

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.users.usersdb.User
import kotlinx.android.synthetic.main.user_card.view.*

class UserListAdapter(context:Context,users:List<User>) : BaseAdapter(){
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(p0: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(p0: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var idTextView:TextView
        var nameTextView:TextView

        init {
            idTextView=itemView.findViewById(R.id.user_id_textview)
            nameTextView=itemView.findViewById(R.id.user_name_textview)
        }

        fun bind(item:String){
            with(itemView){
              //  nameTextView=users[position].name
            }
        }
    }
}