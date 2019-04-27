package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.bean.FriendListGroupByIdBeanResult
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.view.activity.FriendMessageActivity
import kotlinx.android.synthetic.main.friend_list_layout_item.view.*

class FriendListAdapter(
    context: Context,
    list: List<FriendListGroupByIdBeanResult>
) : RecyclerView.Adapter<FriendListAdapter.MyViewHolder>() {

    var context = context
    var list : List<FriendListGroupByIdBeanResult> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.friend_list_layout_item,null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        FrescoUtil.setPic(list.get(p1).headPic,p0.friend_head_image)
        p0.friend_name_text.setText(list.get(p1).nickName)
        p0.friend_sign_text.setText("")
        val friendUid = list.get(p1).friendUid
        p0.friend_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(context,FriendMessageActivity::class.java)
                intent.putExtra("friendUid",friendUid)
                context.startActivity(intent)
            }
        })
    }

    class MyViewHolder : RecyclerView.ViewHolder{

        var friend_head_image : SimpleDraweeView
        var friend_name_text : TextView
        var friend_sign_text : TextView
        var friend_linear : LinearLayout

        constructor(view : View) : super(view){
            friend_head_image = view.friend_head_image
            friend_name_text = view.friend_name_text
            friend_sign_text = view.friend_sign_text
            friend_linear = view.friend_linear
        }
    }
}