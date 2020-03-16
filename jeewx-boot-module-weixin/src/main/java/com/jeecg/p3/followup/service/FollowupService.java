package com.jeecg.p3.followup.service;

import com.jeecg.p3.followup.entity.R;
import com.jeecg.p3.followup.entity.Template;

/**
 * 处理与业务系统（随访系统）相关逻辑
 * @author hutu
 * @date 2020-01-02 11:33
 */
public interface FollowupService {

    /**
     * 发送微信模板消息
     * @param template 模板
     * @return 是否成功
     */
    boolean sendMessage(Template template);

    /**
     * 若取消订阅，就更新openId和临时表doctorId的绑定关系 删除置为1
     *
     * @param openid 取消关注人的openId
     * @return
     */
    Boolean delBindUnSubscribe(String openid);

    /**
     * 处理公众号关注事件，通过扫码关注则绑定患者于团队和医生关系，若是非扫码关注则只新增一条患者信息
     * @param openid 微信id
     * @return 业务系统处理结果
     */
    R dealSubEvent(String openid, String parameter);
}
