package com.wd.tech.mvp.view.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.mvp.model.api.ApiServer
import com.wd.tech.mvp.model.bean.CommunityCommentVo
import com.wd.tech.mvp.model.bean.CommunityGreatBean
import com.wd.tech.mvp.model.bean.CommunityListBeanResult
import com.wd.tech.mvp.model.utils.FrescoUtil
import com.wd.tech.mvp.model.utils.RetrofitUtil
import com.wd.tech.mvp.view.activity.FriendShowActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_community.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil


class CommunityListAdapter(context : Context, list : List<CommunityListBeanResult>) : RecyclerView.Adapter<CommunityListAdapter.MyViewHolder>() {
    var context: Context = context
    var list: List<CommunityListBeanResult> = list
    var result: List<CommunityListBeanResult> = ArrayList<CommunityListBeanResult>()
    lateinit var adapter : CommunitycommentListAdapter
    var hashMap : HashMap<String,String> = HashMap()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.item_community, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        var sharedPreferences : SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
        var userId = sharedPreferences.getString("userId", "0")
        var sessionId = sharedPreferences.getString("sessionId", "0")
        hashMap.put("userId",userId)
        hashMap.put("sessionId",sessionId)
        FrescoUtil.setPic(list.get(p1).headPic, p0.user_head_image)
        p0.user_head_image.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var phone = list.get(p1).userId.toString()
                var intent : Intent = Intent(context,FriendShowActivity::class.java)
                intent.putExtra("phone",phone)
                context.startActivity(intent)
            }
        })
        p0.user_head_text.setText(list.get(p1).nickName)
        val currentTime = Date(list.get(p1).publishTime)
        val simpleTiem = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = simpleTiem.format(currentTime)
        p0.user_head_time.setText(time)
        p0.user_head_text_title.setText(list.get(p1).content)
        var file: String = list.get(p1).file
        var length = file.split(",")
        p0.user_praise_num.setText(list.get(p1).praise.toString())
        p0.user_comment_num.setText(list.get(p1).comment.toString())
        if (list.get(p1).whetherGreat==1){
            p0.user_praise_box.isChecked = true
        }else{
            p0.user_praise_box.isChecked = false
        }
        var num_praise = list.get(p1).praise
        var apiServer : ApiServer = RetrofitUtil.instant.SSLRetrofit()
        p0.user_praise_box.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (p0.user_praise_box.isChecked == false){
                    apiServer.getCancelCommunityGreat(hashMap,list.get(p1).id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(object : DisposableObserver<CommunityGreatBean>(){
                            override fun onComplete() {

                            }

                            override fun onNext(t: CommunityGreatBean) {
                                Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                                num_praise = num_praise-1
                                p0.user_praise_num.setText(num_praise.toString())
                            }

                            override fun onError(e: Throwable) {

                            }
                        })
                }else{
                    apiServer.getAddCommunityGreat(hashMap,list.get(p1).id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(object : DisposableObserver<CommunityGreatBean>(){
                            override fun onComplete() {

                            }

                            override fun onNext(t: CommunityGreatBean) {
                                Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                                num_praise = num_praise+1
                                p0.user_praise_num.setText(num_praise.toString())
                            }

                            override fun onError(e: Throwable) {

                            }
                        })
                }
            }
        })

        p0.user_comment_image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                iPushComment.onIPushCount(list.get(p1).id, (p1/10)+1,p1)
            }
        })

        if (length.size <= 0) {
            p0.user_image_linear.visibility = View.GONE
            p0.community_image_recycle.layoutManager = GridLayoutManager(context, 0, GridLayoutManager.VERTICAL, false)
            p0.community_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        } else if (length.size == 1) {
            p0.community_image_recycle.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            p0.community_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        } else if (length.size == 2) {
            p0.community_image_recycle.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            p0.community_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        } else {
            p0.community_image_recycle.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            p0.community_image_recycle.adapter = CommunityImageListAdapter(context, length, file)
        }
        var communityCommentVoList: List<CommunityCommentVo> = list.get(p1).communityCommentVoList
        p0.community_comment_recycle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        p0.community_comment_recycle.adapter = CommunitycommentListAdapter(context, communityCommentVoList, 2)
        if (list.get(p1).comment > 3) {
            p0.comment_text_show.visibility = View.VISIBLE
            p0.comment_text_show.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    p0.comment_text_show.visibility = View.GONE
                    var commentLinearPara = p0.comment_linear.layoutParams
                    commentLinearPara.height = LinearLayout.LayoutParams.MATCH_PARENT
                    commentLinearPara.width = LinearLayout.LayoutParams.WRAP_CONTENT
                    p0.comment_linear.setLayoutParams(commentLinearPara)
                    var type: Int = 1
                    adapter = CommunitycommentListAdapter(context, communityCommentVoList, type)
                    p0.community_comment_recycle.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            })
        } else {
            p0.comment_text_show.visibility = View.GONE
            var type: Int = 2
            adapter = CommunitycommentListAdapter(context, communityCommentVoList, type)
            p0.community_comment_recycle.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        if (list.get(p1).comment == 0) {
            p0.community_comment_recycle.visibility = View.GONE
            p0.comment_text_show.visibility = View.VISIBLE
            var commentLinearPara = p0.comment_linear.layoutParams
            commentLinearPara.height = 110
            commentLinearPara.width = LinearLayout.LayoutParams.MATCH_PARENT
            p0.comment_linear.setLayoutParams(commentLinearPara)
            p0.comment_text_show.setText("暂时还没有评论")
            adapter.notifyDataSetChanged()
        }else if (list.get(p1).comment == 1){
            var commentLinearPara = p0.comment_linear.layoutParams
            commentLinearPara.height = 100
            commentLinearPara.width = LinearLayout.LayoutParams.MATCH_PARENT
            p0.comment_linear.setLayoutParams(commentLinearPara)
            adapter.notifyDataSetChanged()
        }else if(list.get(p1).comment == 2){
            var commentLinearPara = p0.comment_linear.layoutParams
            commentLinearPara.height = 180
            commentLinearPara.width = LinearLayout.LayoutParams.MATCH_PARENT
            p0.comment_linear.setLayoutParams(commentLinearPara)
            adapter.notifyDataSetChanged()
        }
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        var user_head_image: SimpleDraweeView
        var user_head_text: TextView
        var user_head_time: TextView
        var user_head_text_title: TextView
        var user_image_linear: LinearLayout
        var community_image_recycle: RecyclerView
        var user_praise_num: TextView
        var user_comment_num: TextView
        var comment_text_show: TextView
        var community_comment_recycle: RecyclerView
        var comment_linear: LinearLayout
        var user_praise_box: CheckBox
        var user_comment_image: ImageView

        constructor(view: View) : super(view) {
            user_head_image = view.user_head_image
            user_head_text = view.user_head_text
            user_head_time = view.user_head_time
            user_head_text_title = view.user_head_text_title
            user_image_linear = view.user_image_linear
            community_image_recycle = view.community_image_recycle
            user_praise_num = view.user_praise_num
            user_comment_num = view.user_comment_num
            community_comment_recycle = view.community_comment_recycle
            comment_text_show = view.comment_text_show
            comment_linear = view.comment_linear
            user_praise_box = view.user_praise_box
            user_comment_image = view.user_comment_image
        }
    }

    interface IPushComment{
        fun onIPushCount(id : Int,pageIndex : Int,index : Int)
    }

    lateinit var iPushComment: IPushComment

    fun setIPushCommentListener(iPushComment: IPushComment){
        this.iPushComment = iPushComment
    }
}