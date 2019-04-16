package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.CommunityCommentVo
import kotlinx.android.synthetic.main.community_comment_list_layout.view.*

class CommunitycommentListAdapter(context: Context,list : List<CommunityCommentVo>,type : Int) : RecyclerView.Adapter<CommunitycommentListAdapter.MyViewHolder>() {
    var context : Context = context
    var list : List<CommunityCommentVo> = list
    var type : Int = type
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.community_comment_list_layout,p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list.size>3){
            if (type > 1){
                return 3
            }else{
                return list.size
            }
        }else{
            return list.size
        }
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.user_comment_name.setText(list.get(p1).nickName+":")
        p0.user_comment_text.setText(list.get(p1).content)
    }

    class MyViewHolder : RecyclerView.ViewHolder{
        var user_comment_name : TextView
        var user_comment_text : TextView
        constructor(view : View) : super(view){
            user_comment_name = view.user_comment_name
            user_comment_text = view.user_comment_text
        }
    }
}