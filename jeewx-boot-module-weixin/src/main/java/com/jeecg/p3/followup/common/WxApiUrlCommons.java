package com.jeecg.p3.followup.common;

/**
 * 调用微信接口的URL
 * @author hutu
 * @date 2020-01-01 14:57
 */

public class WxApiUrlCommons {

    public static final String OAUth_URL ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * 微信授权获取code的地址
     */
    public static final String GET_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

}

