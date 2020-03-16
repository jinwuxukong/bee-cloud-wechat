package com.jeecg.p3.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.jeecg.p3.core.annotation.SkipAuth;
import com.jeecg.p3.core.enums.SkipPerm;
import com.jeecg.p3.followup.common.FollowUpConstants;
import com.jeecg.p3.followup.common.WxApiUrlCommons;
import com.jeecg.p3.followup.entity.PatientInfoForm;
import com.jeecg.p3.followup.entity.Template;
import com.jeecg.p3.followup.entity.WxUserInfo;
import com.jeecg.p3.followup.http.WxMappingJackson2HttpMessageConverter;
import com.jeecg.p3.followup.service.FollowupService;
import com.jeecg.p3.weixin.util.WeiXinQrcodeUtil;
import net.sf.json.JSONObject;
import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 随访系统后台调用的Http service接口
 * @author hutu
 * @date 2020-01-01 14:54
 */
@Controller
@RequestMapping("/followup")
public class FollowUpServerController {
    public final static Logger log = LoggerFactory.getLogger(FollowUpServerController.class);

    @Autowired
    private FollowupService followupService;

    /**
     * 创建带参数的二维码
     * @param parameter 参数
     * @return 带参数二维码地址
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @ResponseBody
    @GetMapping("/createQrcode/{parameter}")
    public String createQrcode(@PathVariable("parameter") String parameter) {
        String qrUrl = WeiXinQrcodeUtil.getTemporaryQrcode(FollowUpConstants.JWID, parameter, 1000);
        log.info("---带参数二维码地址为： {}", qrUrl);
        return qrUrl;
    }

    /**
     * 跳转患者量表记录，对应公众号中随访记录菜单
     *
     * @throws Exception
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/followupRecord")
    public void scaleHistory(HttpServletRequest request, HttpServletResponse response,String openid,String parameter) throws IOException {
//        WxUserInfo wxInfo = getUserWxInfo(openid);
        response.sendRedirect(FollowUpConstants.PAGE_ADDRESS+"/followupRecord?openId="+openid);
    }

    /**
     * 跳转我的医生页面，对应公众号中我的医生菜单
     *
     * @throws Exception
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/myDoctor")
    public void myDoctor(HttpServletRequest request, HttpServletResponse response,String openid,String parameter) throws IOException{
//        WxUserInfo wxInfo = getUserWxInfo(openid);
        response.sendRedirect(FollowUpConstants.PAGE_ADDRESS+"/team?openId="+openid);
    }

    /**
     * 跳转患者信息认证，对应公众号中我的菜单
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/homePage")
    public void patientAuth(HttpServletRequest request, HttpServletResponse response,String openid,String parameter) throws IOException {
        response.sendRedirect(FollowUpConstants.PAGE_ADDRESS+"/homePage?openId="+openid);
    }

    /**
     * 发送模板消息---随访表单
     *
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @PostMapping("/template/send")
    @ResponseBody
    public Boolean followupRemind(@RequestBody Template template) {
        return followupService.sendMessage(template);
    }


    /**
     * 在微信中请求此接口可获取openId（微信授权获取）
     *
     * @param response 返回体
     * @param method 最终需要跳转的方法
     * @param parameter 需要带上的参数
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/oauth")
    public void oauth(HttpServletResponse response,String method,String parameter) {
        log.info("coming===oauth method={} parameter={}",method,parameter);
        String codeUrl = WxApiUrlCommons.OAUth_URL;
        String text = "";
        if(StrUtil.isNotEmpty(parameter)){
            text = "?parameter="+parameter;
        }
        //实际写该项目的域名，或者本机系统调用
        String path = FollowUpConstants.WECHAT_DOMAIN + "/followup/getCode"+text;
        try {
            //用utf8处理path
            path = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = codeUrl.replace("APPID", FollowUpConstants.APPID)
                .replace("REDIRECT_URI", path)
                .replace("SCOPE", "snsapi_base");
        if(StringUtils.isNotEmpty(method)){
            url = url.replace("STATE",method);
        }
        try {
            //发送请求
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2，用户确认之后要重定向 redirect_uri/?code=CODE&state=STATE ，所以拿到code
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response,String parameter) throws Exception {
        log.info("coming===getCode");
        RestTemplate template = new RestTemplate();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        log.info("============获取到用户授权的code=" + code + "=====================");

        //2，拿到code之后，请求认证服务器 拿到access_token
        String apiUrl = WxApiUrlCommons.GET_CODE_URL;

        String url = apiUrl.replace("APPID", FollowUpConstants.APPID)
                .replace("SCOPE", "snsapi_base")
                .replace("SECRET", FollowUpConstants.APP_SECRET)
                .replace("CODE", code);

        log.info("============再次重定向的地址=" + url + "=====================");

        //解决微信返回值 text/plain 格式转化成json
        template.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        String jsonStr = template.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        log.info("============微信授权后返回的json=" + jsonObject + "=====================");
        String openid = (String) jsonObject.get("openid");
        String responseUrl = FollowUpConstants.WECHAT_DOMAIN+"/followup/"+state+"?openid="+openid;
        if(StringUtils.isNotEmpty(parameter)){
            responseUrl+="&parameter="+parameter;
        }
        response.sendRedirect(responseUrl);
    }

    /**
     * 获取微信用户的信息
     *
     * @param openid
     * @return
     */
    private WxUserInfo getUserWxInfo(String openid) {
        RestTemplate template = new RestTemplate();
        String access_token = WeiXinHttpUtil.getRedisWeixinToken(FollowUpConstants.JWID);
        //解决微信返回值 text/plain 格式转化成json
        template.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        //3，带着access_token 和openId 去获取资源
        String urlResource = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        urlResource = urlResource.replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);
        //发送请求
        JSONObject userAccountInfo = template.getForObject(urlResource, JSONObject.class);
        //json转对象
        WxUserInfo wxUserInfo = JSON.parseObject(userAccountInfo.toString(), WxUserInfo.class);
        System.out.println("userAccountInfo = " + userAccountInfo.toString());
        return wxUserInfo;
    }

    /**
     * 测试发送素材
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @SkipAuth(auth = SkipPerm.SKIP_ALL)
    @GetMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("openId", "123456");
        String viewName = "weixin/back/followup/index.vm";
        ViewVelocity.view(request, response, viewName, velocityContext);
    }

}
