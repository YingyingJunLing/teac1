package com.wd.tech.mvp.model.api

import com.wd.tech.mvp.model.api.Api.Companion.BANNERSHOW
import com.wd.tech.mvp.model.bean.BannerShowBean
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiServer {
    //轮播
    @GET(BANNERSHOW)
    fun getBannerShow() : Observable<BannerShowBean>
}