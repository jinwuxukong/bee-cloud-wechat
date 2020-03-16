package com.jeecg.p3.followup.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 随访系统的常量
 */
@Component
public class FollowUpConstants {
    public final static Logger log = LoggerFactory.getLogger(FollowUpConstants.class);
    /**
     * 捷微的公众号的 依米云医
     */
    public static String JWID;


    /**
     * 依米云医的appid
     * <p>
     * wx7eace18d83f268a5
     */
    public static String APPID;

    /**
     * serect
     */
    public static String APP_SECRET;


    /**
     * 随访系统的域名/IP
     */
    public static String FOLLOWUP_DOMAIN;

    /**
     * 捷微域名
     */
    public static String WECHAT_DOMAIN;

    /**
     * 患者扫码二维码---医生id和患者openId绑定--->随访系统接口地址
     */
    public static String DOCTOR_PATIENT_BIND_URL;

    /**
     * 患者填写个人信息--->随访系统接口地址
     */
    public static String INSERT_PATIENT_URL;

    /**
     * 检验患者openid是否绑定医生--->随访系统接口地址
     */
    public static String VERIFY_PATIENT_OPENID;

    /**
     * 微信页面展示地址
     */
    public static String PAGE_ADDRESS;

    /**
     * 关注消息绑定模板id
     */
    public static String NEW_SITEM;

    /**
     * 若取消订阅，就更新openId和临时表doctorId的绑定关系 删除置为1
     */
    public static String DEL_BIND_UNSUBSCRIBE;

    /**
     * 扫码关的注事件需要发送的关注模板id
     */
    public static String SCAN_SUB_TEMPLATE_ID;
    @Value("${followup.scanSubTemplateId}")
    public void setScanSubTemplateId(String templateId) {
        FollowUpConstants.SCAN_SUB_TEMPLATE_ID = templateId;
    }

    /**
     * 非扫码的关注事件需要发送的关注模板id
     */
    public static String NOT_SCAN_SUB_TEMPLATE_ID;
    @Value("${followup.notScanSubTemplateId}")
    public void setNotScanSubTemplateId(String templateId) {
        FollowUpConstants.NOT_SCAN_SUB_TEMPLATE_ID = templateId;
    }

    @Value("${followup.JWID}")
    public void setJWID(String JWID) {
        FollowUpConstants.JWID = JWID;
        log.info("JWID = " + JWID);
    }

    @Value("${followup.APPID}")
    public void setAPPID(String APPID) {
        FollowUpConstants.APPID = APPID;
    }

    @Value("${followup.APPSECRET}")
    public void setAppSecret(String appSecret) {
        APP_SECRET = appSecret;
    }

    @Value("${followup.FOLLOWUPDOMAIN}")
    public void setFollowupDomain(String followupDomain) {
        FOLLOWUP_DOMAIN = followupDomain;
        System.out.println("=====当前请求接口======"+followupDomain);
        FollowUpConstants.VERIFY_PATIENT_OPENID = followupDomain + "/jeewx/verifyPatientOpenId?openId=";
        FollowUpConstants.DOCTOR_PATIENT_BIND_URL = followupDomain + "/jeewx/doctorPatientBind?doctorId={doctorId}&openId={openId}";
        FollowUpConstants.INSERT_PATIENT_URL = followupDomain + "/jeewx/patientForm";
        FollowUpConstants.DEL_BIND_UNSUBSCRIBE = followupDomain + "/jeewx/delBindUnSubscribe?openId=";
    }

    @Value("${followup.WECHATDOMAIN}")
    public void setWechatDomain(String wechatDomain) {
        WECHAT_DOMAIN = wechatDomain;
    }

    @Value("${followup.PAGEADDRESS}")
    public void setPageAddress(String pageAddress) {
        FollowUpConstants.PAGE_ADDRESS = pageAddress;
    }

    @Value("${followup.NEWSITEM}")
    public void setNewSitem(String newSitem) {
        FollowUpConstants.NEW_SITEM = newSitem;
    }

    /**
     * 带参数二维码前缀
     */
    public static String QR_SCENE = "qrscene_";
}
