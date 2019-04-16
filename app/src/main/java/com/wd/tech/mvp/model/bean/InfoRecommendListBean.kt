package com.wd.tech.mvp.model.bean

class InfoRecommendListBean
{/**
 * result : [{"collection":4,"id":63,"releaseTime":1553064683000,"share":4,"source":"哈哈网","summary":"我们不再依靠大量查询来寻求自己需要的知识，而是引导更多信息主动呈现在我们面前。","thumbnail":"http://img.zhiding.cn/5/473/liiX2naemnf3A.jpg?rand=187","title":"脱离时代需求的搜索引擎，会不会被人工智能APP取代？","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":2,"id":62,"releaseTime":1553064558000,"share":0,"source":"亚马逊网","summary":"早在 2017 年，笔者就曾在一篇文章中断言，AI 将成为支撑亚马逊竞争优势的重要基础。就拿\u201c亚马逊进军医疗保健行业\u201d这一\u201c旧闻\u201d来说，鲜有人意识到，AI 技术将在这一波跨界尝试中扮演关键角色。","thumbnail":"http://img.zhiding.cn/5/498/liK114PK66S2.jpg?rand=154","title":"亚马逊：用AI瞄准全球10万亿美元的医疗健康大机遇","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":0,"id":61,"releaseTime":1553064018000,"share":1,"source":"雨天","summary":"主流在线法律服务正在认真利用加密货币实现部分事务的自动化处理，同时降低所有用户的服务享受门槛。","thumbnail":"http://img.zhiding.cn/5/140/li9bFzxtlSE4k.jpg?rand=136","title":"区块链如何帮助人们更方便搞定法律服务？","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":1,"id":60,"releaseTime":1553063676000,"share":0,"source":"侏罗纪","summary":"时至今日，加密货币与智能合约平台当中开始出现越来越多安全漏洞，而其中部分漏洞甚至源自这些平台的构建基础。","thumbnail":"http://img.zhiding.cn/5/438/liWMCBxLdCi6.jpg?rand=94","title":"威胁不止有51%攻击，区块链为何频遭黑客入侵？","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":0,"id":59,"releaseTime":1553063316000,"share":0,"source":"科技行者","summary":"2019MWC上，中国移动以\u201c智慧连接 点亮美好未来\u201d为主题参展，围绕5G技术、物联网、数字家庭、国际业务、终端等领域，展示了最新技术、产品、5G发展计划及首款自主品牌5G终端\u201c先行者一号\u201d等。","thumbnail":"http://img.zhiding.cn/5/474/lixeaV84DvtM.jpg?rand=116","title":"中国移动亮相2019世界移动大会 展示5G发展计划并推出首款自主品牌5G终端","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":1,"id":58,"releaseTime":1553063178000,"share":0,"source":"中原网","summary":"eMBB（增强移动宽带）、URLLC（高可靠低时延连接）、mMTC（海量物联），这三个关键字是从专业通信角度给5G定义的三大应用场景。大量的新型技术会应用到5G通信中，今天我们不讨论技术的先进性，只从上述三个维度来畅享一下未来的5G生活。","thumbnail":"http://img.zhiding.cn/5/642/liZsoBMTFzZoU.jpg?rand=140","title":"5G元年话5G：让生活再快一些","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":1,"id":57,"releaseTime":1553062827000,"share":0,"source":"华为","summary":"华为在北研所举办的 5G 发布会暨2019世界移动大会预沟通会上，用一系列 5G 新品和 5G 成绩告诉大家，5G 近在咫尺。","thumbnail":"http://img.zhiding.cn/5/784/liz9zIiXHam2E.jpg?rand=191","title":"华为：5G基站部署要像搭积木一样便捷，AI要无处不在","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":0,"id":56,"releaseTime":1553040466000,"share":0,"source":"科技行者","summary":"通过谷歌BigQuery平台交付的服务项目，能够帮助您轻松搜索并分析区块链交易。","thumbnail":"http://img.zhiding.cn/5/966/liXq33UXpxgo6.jpg?rand=43","title":"谷歌踏入区块链搜索 触及八大最活跃网络完整数据集","whetherAdvertising":2,"whetherCollection":2,"whetherPay":2},{"collection":0,"id":0,"infoAdvertisingVo":{"content":"八维教育","id":1,"pic":"http://mobile.bwstudent.com/images/tech/ad/bw.png","url":"http://www.bwie.com"},"share":0,"whetherAdvertising":1,"whetherCollection":0,"whetherPay":0},{"collection":1,"id":53,"releaseTime":1539585103000,"share":1,"source":"高街高参","summary":"两天前，马云现身杭州云栖小镇，这次他的露面不是因为阿里巴巴集团的活动，也不是业界峰会，而是为政府站台，参加杭州市为打造全国数字经济第一城举办的动员大会。","thumbnail":"https://img.huxiucdn.com/article/cover/201810/14/123637310014.jpg?imageView2/1/w/710/h/400/|imageMogr2/strip/interlace/1/quality/85/format/jpg","title":"马云力挺杭州\u201c数字经济\u201d第一城之后，我先咽下这口泡沫","whetherAdvertising":2,"whetherCollection":2,"whetherPay":1}]
 * message : 查询成功
 * status : 0000
 */

var message: String? = null
var status: String? = null
var result: List<ResultBean>? = null

class ResultBean {
    /**
     * collection : 4
     * id : 63
     * releaseTime : 1553064683000
     * share : 4
     * source : 哈哈网
     * summary : 我们不再依靠大量查询来寻求自己需要的知识，而是引导更多信息主动呈现在我们面前。
     * thumbnail : http://img.zhiding.cn/5/473/liiX2naemnf3A.jpg?rand=187
     * title : 脱离时代需求的搜索引擎，会不会被人工智能APP取代？
     * whetherAdvertising : 2
     * whetherCollection : 2
     * whetherPay : 2
     * infoAdvertisingVo : {"content":"八维教育","id":1,"pic":"http://mobile.bwstudent.com/images/tech/ad/bw.png","url":"http://www.bwie.com"}
     */

    var collection: Int = 0
    var id: Int =0
    var releaseTime: Long = 0
    var share: Int = 0
    var source: String? = null
    var summary: String? = null
    var thumbnail: String? = null
    var title: String? = null
    var whetherAdvertising: Int = 0
    var whetherCollection: Int = 0
    var whetherPay: Int = 0
    var infoAdvertisingVo: InfoAdvertisingVoBean? = null

    class InfoAdvertisingVoBean {
        /**
         * content : 八维教育
         * id : 1
         * pic : http://mobile.bwstudent.com/images/tech/ad/bw.png
         * url : http://www.bwie.com
         */

        var content: String? = null
        var id: Int = 0
        var pic: String? = null
        var url: String? = null
    }
}
}