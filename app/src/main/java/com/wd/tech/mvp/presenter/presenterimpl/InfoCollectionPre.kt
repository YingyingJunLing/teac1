package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.activity.MyCollectionActivity
import com.wd.tech.mvp.view.contract.Contract

class InfoCollectionPre(myCollectionActivity: MyCollectionActivity) : BasePresenter<Contract.IInfoCollectionView>(),Contract.IInfoCollectionPre {

    override fun onIInfoCollectionPre(hashMap: HashMap<String, String>, page: Int, count: Int) {

    }
}