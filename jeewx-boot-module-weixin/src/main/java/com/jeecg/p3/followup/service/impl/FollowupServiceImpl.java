package com.jeecg.p3.followup.service.impl;

import com.jeecg.p3.followup.common.FollowUpConstants;
import com.jeecg.p3.followup.entity.R;
import com.jeecg.p3.followup.entity.Template;
import com.jeecg.p3.followup.service.FollowupService;
import net.sf.json.JSONObject;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

/**
 * 处理与业务系统（随访系统）相关逻辑
 * @author hutu
 * @date 2020-01-02 11:32
 */
@Service("followupServiceImpl")
public class FollowupServiceImpl implements FollowupService {

    public final static Logger log = LoggerFactory.getLogger(FollowupService.class);


    /**
     * @param template
     * @return
     */
    @Override
    public boolean sendMessage(Template template) {
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WeiXinHttpUtil.getRedisWeixinToken(FollowUpConstants.JWID);
        log.info("================开始发送模板消息=====================");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", template.getToUser());
        jsonObject.put("template_id", template.getTemplateId());
        //是否要跳转到第三方的链接
        if (template.getUrl() != null) {
            jsonObject.put("url", template.getUrl());
        }
        //设置data参数
        Map<String, String> map = template.getData();
        JSONObject data = new JSONObject();
        //遍历所有的key，给他们一一绑定参数
        Set<String> keys = map.keySet();
        keys.forEach(key -> {
            JSONObject temp = new JSONObject();
            temp.put("value", map.get(key));
//            temp.put("color", "#173177");
            data.put(key, temp);
        });
        jsonObject.put("data", data);
        log.info("====================发送的模板消息 data" + data + "=============================");
        RestTemplate restTemplate = new RestTemplate();
        JSONObject result = restTemplate.postForObject(postUrl, jsonObject, JSONObject.class);
        log.info("result = " + result);
        return result.get("errcode").toString().equals("0");
    }

    /**
     * 若患者取消订阅，就更新openId和临时表doctorId的绑定关系删除置为1
     *
     * @param openid 取消关注用户的openId
     * @return 是否成功
     */
    @Override
    public Boolean delBindUnSubscribe(String openid) {
        String url = FollowUpConstants.DEL_BIND_UNSUBSCRIBE + openid;
        RestTemplate restTemplate = new RestTemplate();
        Boolean flag = restTemplate.getForObject(url, Boolean.class);
        return flag;
    }

    /**
     * 业务系统处理关注事件或扫码事件
     * @param openid 微信openId
     * @param parameter 参数
     * @return 结果
     */
    @Override
    public R dealSubEvent(String openid, String parameter) {
        log.info("-----处理扫码关注事件-----");
        String url = FollowUpConstants.VERIFY_PATIENT_OPENID + openid + "&parameter=" + parameter;
        RestTemplate template = new RestTemplate();
        R r = template.getForObject(url, R.class);
        return r;
    }

}
