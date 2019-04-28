package com.wd.tech.mvp.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.wd.tech.R
import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        search_add_edit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_add_edit.text.toString().trim().length>0){
                    friend_linear.visibility = View.VISIBLE
                    group_linear.visibility = View.VISIBLE
                }else{
                    friend_linear.visibility = View.GONE
                    group_linear.visibility = View.GONE
                }
            }
        })
    }
}
