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
import com.wd.tech.mvp.model.bean.FriendListGroupByIdBeanResult
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.view.adapter.FriendMessageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_friend_message.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap
import cn.jmessage.biz.httptask.task.GetEventNotificationTaskMng.EventEntity


class FriendMessageActivity : AppCompatActivity() {

    var friendUid : Int = 0
    lateinit var adapter : FriendMessageAdapter
    var hashMap: HashMap<String, String> = HashMap()
    lateinit var phone : String

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun getEvent(friendListGroupByIdBeanResult : FriendListGroupByIdBeanResult){
        friendUid = friendListGroupByIdBeanResult.friendUid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_message)
        JMessageClient.registerEventReceiver(this)
        EventBus.getDefault().register(this)
        Log.i("用户ID",friendUid.toString())
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
                    var createSingleConversation = Conversation.createSingleConversation(phone)
                    val singleConversation = JMessageClient.getSingleConversation(phone)
                    var list = singleConversation.allMessage
                    adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                    friend_message_recycle.adapter = adapter
                    friend_message_recycle.layoutManager = LinearLayoutManager(this@FriendMessageActivity,LinearLayoutManager.VERTICAL,false)
                    user_push_text.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var edit_text = user_push_edit.text.toString()
                            if (user_push_edit.text.toString().length!=0){
                                var message = JMessageClient.createSingleTextMessage(phone, edit_text)
                                JMessageClient.sendMessage(message)
                                user_push_edit.setText(null)
                            }
                        }
                    })
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    fun onEventMainThread(event: EventEntity) {
        val singleConversation = JMessageClient.getSingleConversation(phone)
        var list = singleConversation.allMessage
        adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
        friend_message_recycle.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        JMessageClient.unRegisterEventReceiver(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }
}
