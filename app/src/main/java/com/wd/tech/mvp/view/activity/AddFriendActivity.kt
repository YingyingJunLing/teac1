package com.wd.tech.mvp.view.activity

import android.content.Intent
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
        text_add_finsh.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })
        search_add_edit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_add_edit.text.toString().trim().length>0){
                    friend_linear.visibility = View.VISIBLE
                    group_linear.visibility = View.VISIBLE
                    friend_text.setText(search_add_edit.text.toString())
                    friend_linear.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var intent : Intent = Intent(this@AddFriendActivity,FriendShowActivity::class.java)
                            intent.putExtra("phone",search_add_edit.text.toString())
                            startActivity(intent)
                        }
                    })
                    group_text.setText(search_add_edit.text.toString())
                }else{
                    friend_linear.visibility = View.GONE
                    group_linear.visibility = View.GONE
                    friend_text.setText("")
                    group_text.setText("")
                }
            }
        })
    }
}
