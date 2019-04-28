package com.wd.tech.mvp.view.frag

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioGroup
import com.wd.tech.R
import com.wd.tech.mvp.view.activity.AddFriendActivity
import kotlinx.android.synthetic.main.add_friend_pop.view.*
import kotlinx.android.synthetic.main.frag_message_layout.*
import kotlinx.android.synthetic.main.frag_message_layout.view.*

class MessageFragment : Fragment(){

    var fragMessagePeopleList : FragMessagePeopleList = FragMessagePeopleList()
    var fragMessageShow : FragMessageShow = FragMessageShow()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = View.inflate(context,R.layout.frag_message_layout,null)
        activity!!.supportFragmentManager.inTransaction() {
            replace(R.id.fragment_message,fragMessageShow)
        }
        view.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId){
                    R.id.btn1 -> activity!!.supportFragmentManager.inTransaction() {
                        replace(R.id.fragment_message,fragMessageShow)
                    }
                    R.id.btn2 -> activity!!.supportFragmentManager.inTransaction() {
                        replace(R.id.fragment_message,fragMessagePeopleList)
                    }
                }
            }
        })
        view.add_friend_image.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var v: View = LayoutInflater.from(activity).inflate(R.layout.add_friend_pop,null)
                var pop : PopupWindow = PopupWindow(v,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true)
                pop.isTouchable = true
                pop.setBackgroundDrawable(object : ColorDrawable(Color.WHITE){})
                pop.showAsDropDown(add_friend_image,-20,20)
                v.add_friend_text.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        var intent : Intent = Intent(activity,AddFriendActivity::class.java)
                        startActivity(intent)
                    }
                })
            }
        })
        return view
    }
    inline fun FragmentManager.inTransaction(fun1: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().fun1().commit()
    }
}