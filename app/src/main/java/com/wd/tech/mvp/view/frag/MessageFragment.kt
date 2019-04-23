package com.wd.tech.mvp.view.frag

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.wd.tech.R
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
        return view
    }
    inline fun FragmentManager.inTransaction(fun1: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().fun1().commit()
    }
}