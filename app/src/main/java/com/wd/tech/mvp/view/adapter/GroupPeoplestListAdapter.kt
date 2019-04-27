package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.InitFriendListBeanResult
import kotlinx.android.synthetic.main.friend_group_item.view.*

class GroupPeoplestListAdapter(context : Context, list : List<InitFriendListBeanResult>) : RecyclerView.Adapter<GroupPeoplestListAdapter.MyViewHolder>(){

    var context : Context = context
    var list : List<InitFriendListBeanResult> = list
    var hashMap : HashMap<String,String> = HashMap()
    lateinit  var friend_list_recycle : RecyclerView

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.friend_group_item,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.friend_list_recycle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        friend_list_recycle = p0.friend_list_recycle
        p0.group_friend_name.setText(list.get(p1).groupName)
        p0.Group_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (p0.group_box.isChecked==true){
                    p0.group_box.isChecked = false
                }else{
                    p0.group_box.isChecked = true
                }
            }
        })
        p0.group_box.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (p0.group_box.isChecked==true){
                    p0.friend_list_recycle.visibility = View.VISIBLE
                }else{
                    p0.friend_list_recycle.visibility = View.GONE
                }
            }
        })
        var friendInfoList = list.get(p1).friendInfoList
        p0.friend_list_recycle.adapter = FriendListAdapter(context,friendInfoList)
    }

    class MyViewHolder : RecyclerView.ViewHolder {

        var group_friend_name : TextView
        var friend_list_recycle : RecyclerView
        var Group_linear : LinearLayout
        var group_box : CheckBox

        constructor(view : View) : super(view){
            group_friend_name = view.group_friend_name
            friend_list_recycle = view.friend_list_recycle
            Group_linear = view.Group_linear
            group_box = view.group_box
        }
    }
}