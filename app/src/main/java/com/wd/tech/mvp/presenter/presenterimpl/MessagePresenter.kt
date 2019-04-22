package com.wd.tech.mvp.presenter.presenterimpl

import com.wd.tech.mvp.presenter.base.BasePresenter
import com.wd.tech.mvp.view.contract.Contract
import com.wd.tech.mvp.view.frag.MessageFragment

class MessagePresenter(messageFragment : MessageFragment) : BasePresenter<Contract.IMessageView>(),Contract.IMessagePre {
    override fun onIMessagePre(hashMap: HashMap<String, String>) {

    }
}