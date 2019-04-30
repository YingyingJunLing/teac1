package com.wd.tech.mvp.view.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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
import cn.jpush.im.android.api.model.Message
import com.donkingliang.imageselector.utils.ImageSelector
import com.wd.tech.mvp.model.utils.AlterAndAnimationUtil
import com.wd.tech.mvp.view.adapter.UserPushImageAdapter
import kotlinx.android.synthetic.main.activity_user_push_comment.*
import kotlinx.android.synthetic.main.dialog_camera_layout.view.*
import java.io.File


class FriendMessageActivity : AppCompatActivity() {

    lateinit var friendUid : String
    lateinit var title : String
    lateinit var adapter : FriendMessageAdapter
    var hashMap: HashMap<String, String> = HashMap()
    lateinit var phone : String
    lateinit var list : MutableList<Message>
    val PHOTO_REQUEST_CAREMA : Int = 1
    val PHOTO_REQUEST_GALLERY : Int = 2
    var alterAndAnimationUtil : AlterAndAnimationUtil = AlterAndAnimationUtil()
    private val REQUEST_CODE = 0x00000011
    var listString : ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_message)
        JMessageClient.registerEventReceiver(this)
        friendUid = intent.getStringExtra("friendUid")
        jump_friend_show.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(this@FriendMessageActivity,FriendShowActivity::class.java)
                intent.putExtra("phone",friendUid)
                startActivity(intent)
            }
        })
        if (friendUid.length==11){
            user_name.setText(friendUid)
            var createSingleConversation = Conversation.createSingleConversation(friendUid)
            val singleConversation = JMessageClient.getSingleConversation(friendUid)
            var list = singleConversation.allMessage
            friend_message_recycle.layoutManager = LinearLayoutManager(this@FriendMessageActivity,LinearLayoutManager.VERTICAL,false)
            adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
            friend_message_recycle.adapter = adapter
            friend_message_recycle.scrollToPosition(list.size-1)
            user_push_text.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    var edit_text = user_push_edit.text.toString()
                    if (user_push_edit.text.toString().length!=0){
                        var message = JMessageClient.createSingleTextMessage(friendUid, edit_text)
                        JMessageClient.sendMessage(message)
                        val singleConversation = JMessageClient.getSingleConversation(friendUid)
                        list = singleConversation.allMessage
                        adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                        friend_message_recycle.adapter = adapter
                        friend_message_recycle.scrollToPosition(list.size-1)
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
                        friend_message_recycle.scrollToPosition(list.size-1)
                        user_push_text.setOnClickListener(object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                var edit_text = user_push_edit.text.toString()
                                if (user_push_edit.text.toString().length!=0){
                                    //发送消息
                                    var message = JMessageClient.createSingleTextMessage(phone, edit_text)
                                    JMessageClient.sendMessage(message)
                                    val singleConversation = JMessageClient.getSingleConversation(phone)
                                    list = singleConversation.allMessage
                                    adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                                    friend_message_recycle.adapter = adapter
                                    friend_message_recycle.scrollToPosition(list.size-1)
                                    user_push_edit.setText(null)
                                }
                            }
                        })
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        }
        //发送图片
        messge_img.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                dialogShow()
            }
        })
    }

    fun onEventMainThread(event: MessageEvent) {
        val singleConversation = JMessageClient.getSingleConversation(phone)
        var list = singleConversation.allMessage
        adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
        friend_message_recycle.adapter = adapter
        friend_message_recycle.scrollToPosition(list.size-1)
    }

    override fun onDestroy() {
        super.onDestroy()
        JMessageClient.unRegisterEventReceiver(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    private fun dialogShow(){
        var view : View = View.inflate(this@FriendMessageActivity,R.layout.dialog_camera_layout,null)
        alterAndAnimationUtil.AlterDialog(this@FriendMessageActivity,view)
        //点击拍摄
        view.film_head_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intentFilm : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intentFilm,PHOTO_REQUEST_CAREMA)
                alterAndAnimationUtil.hideDialog()
            }
        })
        //相册选择
        view.photo_head_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(false)  //设置是否单选
                    .setViewImage(true) //是否点击放大图片查看,，默认为true
                    .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                    .start(this@FriendMessageActivity, REQUEST_CODE) // 打开相册
                alterAndAnimationUtil.hideDialog()
            }
        })
        //点击取消
        view.finsh_head_linear.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                alterAndAnimationUtil.hideDialog()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        listString == null
        if (requestCode == PHOTO_REQUEST_CAREMA && data != null){
            val bitmap : Bitmap = data!!.getParcelableExtra("data")
            //将bitmap转换为uri
            var uri : Uri = Uri.parse(MediaStore.Images.Media.insertImage(this.contentResolver,bitmap,null,null))
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var actualimagecursor = contentResolver.query(uri,proj,null,null,null)
            var actual_image_column_index : Int = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            var img_path : String = actualimagecursor.getString(actual_image_column_index)
            listString.add(img_path)
        }else if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            listString = data.getStringArrayListExtra(
                ImageSelector.SELECT_RESULT
            )
            data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false)
        }
        var flieList : java.util.ArrayList<File> = java.util.ArrayList<File>()
        for (i in listString){
            var file : File = File(i)
            flieList.add(file)
        }
        if (flieList.size>=1){
            if (friendUid.length==11){
                for (i in flieList){
                    val createSingleImageMessage = JMessageClient.createSingleImageMessage(friendUid, i)
                    JMessageClient.sendMessage(createSingleImageMessage)
                    val singleConversation = JMessageClient.getSingleConversation(friendUid)
                    list = singleConversation.allMessage
                    adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                    friend_message_recycle.adapter = adapter
                    friend_message_recycle.scrollToPosition(list.size-1)
                }
            }else{
                for (i in flieList){
                    val createSingleImageMessage = JMessageClient.createSingleImageMessage(phone, i)
                    JMessageClient.sendMessage(createSingleImageMessage)
                    val singleConversation = JMessageClient.getSingleConversation(phone)
                    list = singleConversation.allMessage
                    adapter = FriendMessageAdapter(this@FriendMessageActivity,list!!)
                    friend_message_recycle.adapter = adapter
                    friend_message_recycle.scrollToPosition(list.size-1)
                }
            }
        }
        flieList == null
    }
}
