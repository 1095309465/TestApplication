package com.yf.app2;

/**
 * @author shaun_xu
 * @description TODO
 * @email xxcpqzm@126.com
 * @date 2015年12月15日 下午1:19:04
 */

public class HttpURL亦见 {


    //正式域名
     String TEST_BASE_URL = "http://yyjj.yidaoo.cn:8106";
    //支付域名
     String BASE_PAY_URL = "http://py.rgjfood.cn:8104";
    //上传资源域名地址
     String BASE_UPLOAD_URL = "http://dqs.hscsjc.cn:6018";

    //亦见等级购买列表
     String YJ_LEVEL_LIST = BASE_PAY_URL + "/api/recharge/yjLevelList";

    //设置相册为收费
     String USER_PICTURE_TO_PAY_PICTURE = "/user/picture/toPayPicture";
    /*充值的竹子列表*/
     String URL_LIST_ZUANSHI = BASE_PAY_URL + "/api/recharge/yjDiamondList";
    /*获取支付方式*/ //没用这种方式了
     String URL_GET_PAYTYPE = BASE_PAY_URL + "/wallet/recharge/getPayMode";
    /*支付统一下单接口*/ //充值竹子下单
     String URL_GET_ORDER = BASE_PAY_URL + "/api/pay/yjUnifiedOrderDiamond";

     String yjUnifiedOrderLevel = BASE_PAY_URL + "/api/pay/yjUnifiedOrderLevel";
    /*会员开通支付统一下单接口*/ //充会员下单
     String URL_MEMBER_UNIFIEDORDER = BASE_PAY_URL + "/api/pay/unifiedOrderMember01";
    /*支付成功的订单信息(是否升级和积分等)*/ //是否支付成功
     String URL_IS_PAY_SUCCEED = BASE_PAY_URL + "/api/pay/getOrderInfo";

    /*获取会员套餐列表*/
     String URL_MEMBER_CHARGE_LIST = BASE_PAY_URL + "/api/recharge/memberList01";

    /*充值的聊币列表*/
     String URL_CURRENCY_LIST = BASE_PAY_URL + "/api/recharge/currencyList01";
    /*聊币下单*/ //充值聊币下单
     String URL_CURRENCY_ORDER = BASE_PAY_URL + "/api/pay/unifiedOrderCurrency01";

    //上报用户信息（参数后续随需求增加）
     String URL_REPORT_USERINFO = "/user/reportUserInfo";

    //登陆
     String URL_LOGIN = "/user/phoneLogin";

    //微信头像上传接口
     String WE_CHAT_UPLOADFILE = "/user/weChatUploadFile";
    //注册额
     String URL_LOGIN_REPLENISH = "/user/replenishUserInfo";
    //上传图片
     String URL_UPLOADFILE = BASE_UPLOAD_URL + "/user/uploadFile";
     String DQURL_UPLOADFILE = "http://ylapi.xuyangshoes.com.cn:8012/user/uploadFile";
    //批量上传图片
     String URL_UPLOADFILES = BASE_UPLOAD_URL + "/user/uploadFiles";
     String DQURL_UPLOADFILES = "http://ylapi.xuyangshoes.com.cn:8012/user/uploadFiles";
    //发送短信
     String URL_GET_VCODE = "/verifyCode/yjSend";
    //个人基础信息
     String URL_ME_BASE_INFO = "/user/getYjBaseInfo";
    //获取个人信息
     String URL_ME_INFO = "/user/getYjUserInfo";
    //情感信息
     String URL_ME_STATE = "/user/getUserAffectiveState";
    //职业信息
     String URL_ME_WORK = "/user/getUserProfession";
    //更新个人信息
     String URL_ME_INFO_UPDATE = "/user/updateUserInfo";
    //全局参数
     String URL_APP_PARAM = "/app/getAppParam";
    //上传相册
     String URL_UPLOAD_ALBUM = "/user/picture/upload";
    //获取相册列表
     String URL_GET_ALBUM = "/user/picture/list";
    //认证中心
     String URL_GET_AUTH = "/user/auth/center";
    //提交认证
     String URL_USER_AUTH = "/user/auth/submitV1";
    /*发布动态*/
     String URL_ADD_TREND = "/user/dynamic/create";
    /*我的动态列表*/
     String URL_MY_TREND = "/user/dynamic/yjMyList";
    /*动态删除*/
     String URL_DEL_DYNAMIC = "/user/dynamic/delDynamic";

    //聊天人数回写
     String CHAT_WITH_PERSON = "/user/chatWithPerson";

    //动态改为收费
     String TO_PAY_DYNAMIC = "/user/dynamic/toPayDynamic";


    //动态评论点赞
     String REPLIES_FAVOUR = "/user/dynamic/repliesFavour";

    /*关注的动态列表*/
     String URL_FOLLOW_TREND = "/user/dynamic/yjFocusList";
    /*动态点赞*/
     String URL_TREND_FAVOUR = "/user/dynamic/favour/create";
    /*个人资料页面点赞*///个人点赞/用户点赞
     String URL_USER_FAVOUR = "/user/favour/create";
    /*关注*/
     String URL_FOLLOW = "/user/relation/follow";
    /*取消关注*/
     String URL_UN_FOLLOW = "/user/relation/unFollow";
    /*获取评论列表*/

     String URL_TREND_COMMENT_LIST = "/user/dynamic/yjRepliesList";
    /*动态发布评论*/
     String URL_TREND_COMMENT = "/user/dynamic/replies/create";
    /*获取首页视频*/
     String URL_MAIN_VIDEO_LIST = "/user/dynamic/mainVideoList";
    /*首页banner*/
     String URL_MAIN_BANNER_LIST = "/main/banner/yjBannerList";
    /*首页快聊*/
     String URL_MAIN_CHAT_LIST = "/main//chat/list";
    /*访问目标用户个人中心*/ //个人中心首页
     String URL_PERSON_HOME = "/user/yjPersonalCenter";
    //    /*个人中心动态页面*/
    /*我的个人中心*/
     String URL_USERCENTER = "/user/yjUserCenter";
    /*别人的动态列表*/
     String URL_OTHER_TREND = "/user/dynamic/othersList";
    /*礼物列表*/
     String URL_GIFT = "/message/gift/list";
    /*关注的人*/
     String URL_FOLLOW_PERSON_LIST = "/user/relation/followList";
    /*系统最新通知*/
     String URL_SYSTEM_LASTEST_NOTIFICATION = "/user/systemInform/status";
    /*系统通知列表*/
     String URL_SYSTEM_LIST_NOTIFICATION = "/user/systemInform/list";
    /*系统通知标记全部已读*/
     String URL_SYSTEM_INFORM_READ = "/user/systemInform/read";
    /*别人个人中心照片 */
     String URL_OTHER_PHOTO = "/user/picture/othersList";


    /**
     * 相册删除
     */
     String USER_PICTURE_DELPICTURE = "/user/picture/delPicture";

    /*分享视频接口*/
     String URL_SHARE_VIDEO = "/user/share/videoDynaimc";
    /*分享个人接口*/
     String URL_SHARE_INFO = "/user/share/personal";
    /*分享成功记录*/
     String URL_SHARE_SUCCESS = "/user/share/shareLog";
    /*看过我的人*/
     String URL_WHO_SEE_ME = "/user/visit/list";
    /*拉黑/取消拉黑*/
     String URL_BLACKLIST = "/user/relation/black";
    /*去报内容*/
     String URL_REPORT = "/user/complain/list";
    /*提交举报*/
     String URL_REPORT_COMMIT = "/user/complain/yjCreate";
    /*微信登陆*/
     String URL_LOGIN_WX = "/user/wechatLogin";
    /*绑定状态*/
     String URL_BIND_STATUS = "/user/getBindStatus";
    /*绑定微信*/
     String URL_BIND_WX = "/user/bindWechat";
    /*绑定手机号*/
     String URL_BIND_PHONE = "/user/bindPhone";
    /*我的钱包*/
     String URL_GET_MY_POCKET = "/wallet/recharge/yjBalance";
    /*充值banner*/
     String URL_GET_RECHARGE_BANNER = "/wallet/recharge/yjBannerList";


    //提升等级
     String DAILY_LEVEL_TASKS = "user/dailyLevelTasks";


    /*收入明细*/
     String URL_INCOME_DETAIL = "/wallet/bill/earningList";
    /*支出明细*/
     String URL_OUTER_DETAIL = "/wallet/bill/expendList";
    /*照片扣费*/
     String URL_PICTURE_PAY = "/user/picture/pay";
    /*动态扣费*/
     String URL_DYNAMIC_PAY = "/user/dynamic/pay";
    //会员中心
     String URL_MEMBER_CENTER = "/member/center/baseInfo";

    /*购买vip*/
     String URL_MEMBER_PAY = "/member/center/pay";
    /*准备呼叫*/
     String URL_VIDEO_START_CALL = "/message/giftVideoCall/readyCall";
    /*呼叫结果回调*/
     String URL_VIDEO_CALL_RESULT = "/message/giftVideoCall/callResultNotify";
    /*挂断电话*/ //通话结束回调
     String URL_VIDEO_HUNGUP = "/message/giftVideoCall/videoEndNotify";
    /*排行榜——魅力榜*/
     String URL_CHARM = "/rank/list/charm";
    /*排行榜——财力榜*/
     String URL_TREASURE = "/rank/list/treasure";
    /*守护榜*/
     String URL_GUARD = "/rank/list/guard";
    /*认证状态,实名认证状态和视频认证状态*/
     String URL_AUTH_STATE = "/user/auth/rNameAuthentication";
    /*实名认证*/
     String URL_REALNAME_AUTH = "/user/auth/submitRealName";
    /*获取用户实名基本信息*/
     String URL_GETREAL_INFO = "/user/getRealInfo";
    /*获取视频认证示例*/
     String URL_VIDEO_AUTH_DEMO = "/user/auth/authDemoList";
    /*视频认证资料信息*/ //原数据，tag 标签数据
     String URL_VIDEO_AUTH_DATA_LIST = "/user/auth/videoAuthDataList";
    /*提交视频认证*/
     String URL_SUBMIT_VIDEO = "/user/auth/yjSubmitVideo";
    /*快聊认证资料信息*/ //视频认证资料信息，资料管理原数据
     String URL_VIDEO_DATA_LIST = "/user/auth/videoDataList";
    /*资料管理申请*/ //修改视频认证信息
     String URL_SUBMIT_DATA = "/user/auth/yjSubmitData";
    /*修改用户变为经纪人*/
     String URL_BROKER = "/user/broker";

    /*礼物盒*/
     String userMyGiftBoxList = "/user/myGiftBoxList";

    //认证用户的账户明细
     String userAuthList = "/userAuth/list";

    /*经纪人的账户明细。 获取经纪人页面数据*/
     String URL_AGENT_LIST = "/agent/list";
    /*经纪人的收益明细。 获取经纪人页面——收益明细 数据*/
     String URL_AGENT_EARNLIST = "/agent/earnList";
    /*获取提现账户*/ // withdrawalType  1、普通提现 2、聊天经纪人提现 3、推广计划提现 4、拉新活动提现
     String URL_WITHDRAWA = "/Withdrawal/list";

    /*是否绑定提现账户*/
     String URL_IS_BIND_ACCOUNT = "/Withdrawal/isBindAccount";
    /*提现申请*/  // withdrawalType  1、普通提现 2、聊天经纪人提现 3、推广计划提现 4、拉新活动提现
     String URL_APPLY_WITHDRAWAL = "/Withdrawal/applyWithdrawal";
    /*绑定提现账户*/
     String RUL_WITHDRAWAL_BINDACCOUNT = "/Withdrawal/bindAccount";
    /*获取账号绑定状态*/ //是否绑定手机号，是否绑定微信
     String RUL_GET_BIND_STATUS = "/user/getBindStatus";
    /*获取提现明细*/ //提现记录
     String RUL_WITHDRAWAL_LIST = "/Withdrawal/withdrawalList";

    //提现申请
     String withdrawalApplyWithdrawal = "/Withdrawal/applyWithdrawal";


    //提现扣税显示
     String withdrawalApplyWithdrawalTax = "/Withdrawal/applyWithdrawalTax";

    //用户美颜设置
     String userUserBeautySet = "/user/userBeautySet";

    //    通话记录评价//
     String MESSAGE_VOICE_CALL_VOICE_EVALUATION = "/message/voiceCall/voiceEvaluation";


    //获取提现账户
     String withdrawalList = "/Withdrawal/list";

    /*经纪人分享*/
     String URL_SHARE_AGENT = "/user/share/agent";
    /*推广计划分享_邀请好友*/
     String URL_SHARE_PROMOTE = "/user/share/promote";
    /* 获取分享配置 */ //任务夺宝分享，应该还有其他地方也会用到
     String RUL_SHARE_APP = "/user/share/app";

    /*拉新分享*/
     String URL_SHARE_ACTIVITY = "/user/share/activity";

    /* app每日分享 */ //任务夺宝分享成功后调用
     String RUL_appEveryDayShare = "/bamboo/activity/appEveryDayShare";
    /*推广人的账户明细。 获取推广计划页面数据*/
     String URL_PROMOTE_LIST = "/promote/list";
    /*推广人的收益明细*/
     String RUL_PROMOTE_EARN_LIST = "/promote/earnList";
    /*任务夺宝数据*/
     String RUL_TASK_INDIANA_INFO = "/bamboo/activity/taskIndianaInfo";
    /*日常签到。 任务夺宝页面签到*/
     String RUL_TASK_SIGN_IN = "/bamboo/activity/everyDaySignIn";
    /*礼物赠送*/ //source 普通聊天1 视频通话2 视频动态3;  dynamicId source=3时此字段必须传递
     String RUL_GIFT_GIVING = "/message/gift/giving";
    /*领取每日会员礼包*/
     String RUL_MEMBER_EVERYDAY_BAMBOO_REWARDS = "/bamboo/activity/memberEveryDayBambooRewards";
    /*访问记录列表。谁看过我*/
     String URL_VISIT_LIST = "/user/visit/list";
    /*打招呼*/
     String URL_SAY_HI = "/user/sayHi";
    /*获取数据更新情况*/ //可控制红点
     String URL_GET_DATA_UPDATE_INFO = "/user/getDataUpdateInfo";
    /*附近的人*/
     String URL_PEOPLE_NEARBY = "/user/yjPeopleNearby";
    /*附近正在线的人*/
     String URL_PEOPLE_ONLINE_NEARBY = "/user/yjPeopleOnLineNearby";

    /*得到礼物视频通话目标用户个人信息*/
     String URL_GIFT_VIDEO_CALL_PERSONAL_DATA = "/message/giftVideoCall/personalData";
    /*获取视频聊天礼物列表*/
     String URL_VIDEO_GIFT_LIST = "/message/gift/videoGiftList";
    /*礼物邀请视频赠送*/
     String URL_INVITE_GIVING = "/message/gift/inviteGiving";
    /*视频聊天邀请订单详情*/
     String URL_GIFT_VIDEO_ORDER_DETAILS = "/message/giftVideoCall/giftVideoOrderDetails";
    /*快聊视频邀请获取订单状态*/
     String URL_GET_CALL_ORDER_DATA = "/message/giftVideoCall/getCallOrderData";
    /*视频通话邀请取消*/
     String URL_GIFT_VIDEO_CALL_CANCEL_CALL = "/message/giftVideoCall/cancelCall";
    /*视频通话邀请接受*/
     String URL_GIFT_VIDEO_CALL_ACCEPT = "/message/giftVideoCall/accept";
    /*获取视频加时间礼物列表*/
     String URL_VIDEO_ADD_TIME_GIFT_LIST = "/message/gift/videoAddTimeGiftList";
    /*视频通话加时邀请礼物赠送*/
     String URL_ADD_TIME_INVITE_GIVING = "/message/gift/addTimeInviteGiving";
    /*视频通话邀请加时接受*/
     String URL_ACCEPT_ADDTIME = "/message/giftVideoCall/acceptAddTime";
    /*视频通话邀请加时拒绝*///拒绝对方的加时
     String URL_REFUSED_ADDTIME = "/message/giftVideoCall/refusedAddTime";
    /*获取收到的礼物视频状态数据*/ // 收到的最新视频邀请
     String URL_GIFT_VIDEO_CALL_STATUS = "/message/giftVideoCall/status";
    /*收到的视频邀请列表 */
    //  待处理视频聊天邀请列表
     String URL_GIFT_VIDEO_CALL_LIST = "/message/giftVideoCall/giftVideoCallList";
    //  历史视频聊天邀请列表
     String URL_HISTORY_GIFT_VIDEO_CALL_LIST = "/message/giftVideoCall/historyGiftVideoCallList";
    /*待处理邀请标记全部已读*/ //我收到的视频邀请 标记全部已读
     String URL_GIFT_VIDEO_CALL_READMSG = "/message/giftVideoCall/readMsg";
    /*获取评价标签列表*/
     String URL_VIDEO_CALL_GET_EVALUATION_TAG = "/message/videoCall/getEvaluationTag";
    /*通话记录评价*/
     String URL_GIFTVIDEO_CALL_EVALUATION = "/message/giftVideoCall/evaluation";

    /*06通话记录评价*/ //一分钟通话模式视频通话后的评价调用这个接口
     String URL_VIDEO_CALL_EVALUATION = "/message/videoCall/evaluation";
    /*领取注册新人礼包奖励*/
     String URL_NEWPERSON_BAMBOO_REWARDS = "/bamboo/activity/newPersonBambooRewards";
    /*我的等级中心*/

     String URL_USER_CENTER_LEVEL = "/user/yjUserCenterLevel";

    //用户升级提醒
     String LOGIN_REMIND = "/user/loginRemind";


    /*等级说明*/
     String URL_LEVEL_DESCRIPTION = "/user/yjLevelDescription";
    /*当前段位等级权益说明*/ //徽章说明
     String URL_USER_LEVEL_DESCRIPTION = "/user/userLevelDescription";
    /*提升我的等级*/
     String URL_ASCENSION_LEVEL = "/user/ascensionLevel";
    /*登录送积分*/
     String URL_LOGIN_SEND_INTEGRAL = "/user/loginSendIntegral";
    /*更新*/
     String URL_UPDATE_APP = "/app/checkYjUpdate";

    /*视频礼物通话接收方用户是否需要升级提醒*/
     String URL_IS_REMIND = "/user/isRemind";
    /*用户升级提醒变为已提醒*/ //标记已升级过
     String URL_REMIND = "/user/remind";

    /*获取接听状态*/
     String URL_GET_ANSWER_STATUS = "/user/getAnswerStatus";
    /*更新接听状态（前端尽量控制频率）*/
     String URL_UPDATE_ANSWER_STATUS = "/user/updateAnswerStatus";

    /*设置用户通话状态*/ // 通话设置里面
     String URL_CALL_SET_STATU = "/user/userCallSet";
    /*萌新列表*/
     String URL_SPROUTING_LIST = "/user/sproutingList";

    /**
     * 一分钟通话逻辑
     */
    /*发起视频接口*///准备呼叫
     String URL_READY_CALL = "/message/videoCall/readyCall";
    /*是否能接听（被叫方为扣费方时调用）*/
     String URL_VIDEO_IS_CAN_ANSWER = "/message/videoCall/isCanAnswer ";
    /*发起视频通话结果回调接口(未接通，主叫方调用；接通后支出方调用)*///(忙、拒绝(对方是未认证用户余额不足))
     String URL_CALL_RESULT_NOTIFY = "/message/videoCall/callResultNotify";
    /*视频每分钟扣费接口(支出方调用)*/
     String URL_VIDEO_CALL_PAY = "/message/videoCall/pay";
    /*挂断电话*/ //通话结束回调
     String URL_VIDEO_END_NOTIFY = "/message/videoCall/videoEndNotify";

    /*视频通话他人信息*/ //只获取来电视频
     String URL_CALL_USER_DATA_INFO = "/user/getCallUserDataInfo";

    /*语音认证*/
     String URL_SUBMIT_VOICE_AUTH = "/user/auth/submitVoiceAuth";
    /*语音认证资料信息*/ //原数据
     String URL_VOICE_AUTH_DATA_LIST = "/user/auth/voiceDataList";
    /*语音资料管理申请*/
     String URL_SUBMIT_VOICE_DATA = "/user/auth/submitVoiceData";


    /*03是否能接听（被叫方为扣费方时调用）*/
     String MESSAGE_VOICE_CALL_VOICE_IS_CANANSWER = "/message/voiceCall/voiceIsCanAnswer";


    /*得到语音通话目标用户个人信息*/
     String MESSAGE_VOICE_CALL_GET_VOICE_TO_USER_INFO = "/message/voiceCall/getVoiceToUserInfo";


    /**
     * 语音准备呼叫
     */
     String VOICE_READY_CALL = "/message/voiceCall/voiceReadyCall";

    /*挂断电话*/ //语音通话结束回调
     String URL_VOICE_END_NOTIFY = "/message/voiceCall/voiceEndNotify";


    /**
     * 语音呼叫结果回调
     */
     String MESSAGE_VOICE_CALL_VOICE_CALL_RESULT_NOTIFY = "/message/voiceCall/voiceCallResultNotify";


    //语音通话扣费
     String MESSAGE_VOICE_CALL_VOICE_PAY = "/message/voiceCall/voicePay";


}
