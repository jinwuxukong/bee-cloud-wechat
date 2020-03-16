package com.jeecg.p3.followup.controller;

import com.jeecg.p3.core.annotation.SkipAuth;
import com.jeecg.p3.core.enums.SkipPerm;
import com.jeecg.p3.followup.common.FollowUpConstants;
import com.jeecg.p3.followup.http.WxMappingJackson2HttpMessageConverter;
import com.jeecg.p3.redis.JedisPoolUtil;
import com.jeecg.p3.redis.SerializeUtil;
import com.jeecg.p3.weixinInterface.entity.WeixinAccount;
import org.apache.http.client.utils.HttpClientUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
* 描述：</b>JwSystemRegisterController<br>
 *     扫描二维码获取用户信息
* @author alex
* @since：2016年03月23日 18时07分59秒 星期三
* @version:1.0
*/
@Controller
@RequestMapping("/jwSystemGetUser")
public class JwSystemGetUserController extends BaseController{

    String WX_SCAN_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={APPID}&redirect_uri={REUTL}&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect";

    // 千万要记住，这个是微信开放平台的APPID
//    String WX_PLATFROM_APPID = "wx178396dba45bf1a9";
    String WX_PLATFROM_APPID = "wx7eace18d83f268a5";
    // 千万要记住，这个是微信开放平台的APPSECRET
//    String WX_PLATFORM_APPSECRET = "2f0010f2f9a718b40b228bb263bd1ec5";
    String WX_PLATFORM_APPSECRET = "13c4f8de093bfd9fe2a422e722e5b0ad";
    // 你的回调地址
    String scanReUrl = "https://zyzjeewx.utools.club/jwSystemGetUser/wxLoginCallback";
//    String scanReUrl = FollowUpConstants.WECHAT_DOMAIN+"/jwSystemGetUser/wxLoginCallback";

    /**
     * 微信扫码登陆
     * @return
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/doGetUser")
    public void GetUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 获取回调url(非必填，只是附带上你扫码之前要进入的网址，具体看业务是否需要)
        String url = request.getParameter("reurl");

        // 拼接扫码登录url
        String wxLoginurl = WX_SCAN_CODE_URL;
        String encode = URLEncoder.encode(scanReUrl);
        wxLoginurl = wxLoginurl.replace("{APPID}", WX_PLATFROM_APPID).replace("{REUTL}", encode);
        wxLoginurl = response.encodeURL(wxLoginurl);
        response.sendRedirect(wxLoginurl);
    }

    // 拉起微信扫码页地址
    String WX_SCAN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 微信扫码之后获取用户基本信息的地址
    String WX_SCAN_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    /**
     * 获取微信用户信息回调接口
     * @return
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/wxLoginCallback")
    public void loginCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        if (code == null) {
            // 用户禁止授权
        }
        String url = WX_SCAN_URL.replace("APPID", WX_PLATFROM_APPID).replace("SECRET", WX_PLATFORM_APPSECRET)
                .replaceAll("CODE", code);
        url = response.encodeURL(url);
        try {
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

            String jsonStr = template.getForObject(url, String.class);

            System.out.println("jsonStr = " + jsonStr);

//            String result = HttpClientUtils.get(url, null);
//            Map<String, Object> resultMap = JsonHelper.toMap(result);
//            Map<String, Object> resultMap = new HashMap<>();
//            String unionid = (String) resultMap.get("unionid");
//            String access_token = (String) resultMap.get("access_token");
//            String openid = (String) resultMap.get("openid");
            // 这里可以根据获取的信息去库中判断是否存在库中 如果不存在执行以下方法
            // 如果该用户不存在数据库中
            // 获取用户信息
//            url = ConstantHelper.WX_SCAN_GET_USER_INFO.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
//            url = response.encodeURL(url);
//            String userResult = HttpClientUtils.get(url, null);
//            Map<String, Object> userResultMap = JsonHelper.toMap(userResult);
            // 注册一个用户
            Map<String, Object> userResultMap = new HashMap<>();
            String userResult = "";
            System.out.println("扫码登录返回值******************:" + userResult);
            String headimgurl = (String) userResultMap.get("headimgurl");
            // 处理微信名特殊符号问题 过滤图标
            String nickname = (String) userResultMap.get("nickname");
            // 把用户信息存入session中
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回地址
        try {
            String newUrl = request.getParameter("state");
            response.sendRedirect(newUrl);
        } catch (IOException e) {
            // logger.error("url:重定向错误");
        }
    }


}

