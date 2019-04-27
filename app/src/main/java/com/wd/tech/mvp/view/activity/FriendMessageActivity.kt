package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Conversation
import com.wd.tech.R
import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.FriendInfoMationBean
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.view.adapter.FriendMessageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_friend_message.*
import java.util.HashMap
import cn.jpush.im.android.api.event.MessageEvent


class FriendMessageActivity : AppCompatActivity() {

    lateinit var friendUid : String
    lateinit var title : String
    lateinit var adapter : FriendMessageAdapter
    var hashMap: HashMap<String, String> = HashMap()
    lateinit var phone : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_message)
        JMessageClient.registerEventReceiver(this)
        friendUid = intent.getStringExtra("friendUid")
        if (friendUid.length==11){
            user_name.setText(friendUid)
            var createSingleConversation = Conversation.createSingleConversation(friendUid)
            val singleConversation = JMessageClient.getSingleConversation(friendUid)
            var list = singleConversation.allMessage
            friend_message_recycle.layoutManager = LinearLayoutManager(this@FriendMessageActivity,LinearLayoutManager.VERTICAL,false)
            adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
            friend_message_recycle.adapter = adapter
            user_push_text.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    var edit_text = user_push_edit.text.toString()
                    if (user_push_edit.text.toString().length!=0){
                        var message = JMessageClient.createSingleTextMessage(friendUid, edit_text)
                        JMessageClient.sendMessage(message)
                        val singleConversation = JMessageClient.getSingleConversation(friendUid)
                        var list = singleConversation.allMessage
                        adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                        friend_message_recycle.adapter = adapter
                        user_push_edit.setText(null)
                    }
                }
            })
        }else{
            var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
            var sharedPreferences: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            var userId = sharedPreferences.getString("userId", "0")
            var sessionId = sharedPreferences.getString("sessionId", "0")
            hashMap.put("userId", userId)
            hashMap.put("sessionId", sessionId)
            apiServer.getFriendInfoMation(hashMap,friendUid.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<FriendInfoMationBean>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: FriendInfoMationBean) {
                        phone = t.result.phone
                        user_name.setText(phone)
                        var createSingleConversation = Conversation.createSingleConversation(phone)
                        val singleConversation = JMessageClient.getSingleConversation(phone)
                        var list = singleConversation.allMessage
                        friend_message_recycle.layoutManager = LinearLayoutManager(this@FriendMessageActivity,LinearLayoutManager.VERTICAL,false)
                        adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                        friend_message_recycle.adapter = adapter
                        user_push_text.setOnClickListener(object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                var edit_text = user_push_edit.text.toString()
                                if (user_push_edit.text.toString().length!=0){
                                    var message = JMessageClient.createSingleTextMessage(phone, edit_text)
                                    JMessageClient.sendMessage(message)
                                    val singleConversation = JMessageClient.getSingleConversation(phone)
                                    var list = singleConversation.allMessage
                                    adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                                    friend_message_recycle.adapter = adapter
                                    user_push_edit.setText(null)
                                }
                            }
                        })
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        }
    }

    fun onEventMainThread(event: MessageEvent) {
        val singleConversation = JMessageClient.getSingleConversation(phone)
        var list = singleConversation.allMessage
        adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
        friend_message_recycle.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        JMessageClient.unRegisterEventReceiver(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }
}
