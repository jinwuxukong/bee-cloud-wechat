package com.jeecg.p3.followup.entity;

import java.util.Map;

/**
 * 模板的基本信息
 */
public class Template {
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 是接收者openid
     */
    private String toUser;

    /**
     * 模板跳转链接可以拼接参数
     * <p>
     * 我们系统的患教，量表地址拼接患者的id
     * 类似于 /xxx/preview/id?patientId=id
     * 这个请求是无权限校验的
     */
    private String url;

    /**
     * 传递的数据
     */
    private Map<String,String> data;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateId='" + templateId + '\'' +
                ", toUser='" + toUser + '\'' +
                ", url='" + url + '\'' +
                ", data=" + data +
                '}';
    }
}
