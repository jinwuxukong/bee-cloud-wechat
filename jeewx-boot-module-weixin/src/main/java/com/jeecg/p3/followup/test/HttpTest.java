package com.jeecg.p3.followup.test;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpTest {

    /**
     * 不带参数
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendPost(String url) throws IOException {
        // 1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2.生成一个post请求
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            // 3.执行get请求并返回结果
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // 4.处理结果，这里将结果返回为字符串
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (ParseException | IOException e) {
        }
        return result;
    }


    /**
     * 带参数
     *
     * @param url
     * @param jsonString
     * @return
     * @throws IOException
     */
    public static String sendPostJsonStr(String url, String jsonString) throws IOException {
        if (jsonString == null || jsonString.isEmpty()) {
            return sendPost(url);
        }
        String resp = "";
        StringEntity entityStr = new StringEntity(jsonString,
                ContentType.create("text/plain", "UTF-8"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entityStr);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        if (resp == null || resp.equals("")) {
            return "";
        }
        return resp;
    }


    /**
     * 获取access_token 地址
     */
    private static String ACCESS_TOKEN_ADDRESS = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 开发者id
     */
    private static String APPID = "wxdacac90bb1ad9ceb";
    /**
     * 密钥
     */
    private static String APPSECRET = "706744318c6957678b83d307fe8fbc15";

    /**
     * 获取access_token
     *
     * @return
     */
    public static String getAccessToken() {
        String url = ACCESS_TOKEN_ADDRESS.replace("APPID", APPID)
                .replace("APPSECRET", APPSECRET);

        RestTemplate template = new RestTemplate();

        JSONObject result = template.getForObject(url, JSONObject.class);

        System.out.println("result = " + result);

        System.out.println("==========获取access_token============");

        return result.get("access_token").toString();
    }

    /**
     * 获取模板信息的地址
     */
    private static String GET_TEMPLATE_MESSAGE_ADDRESS = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
    private static String SET_TEMPLATE_MESSAGE_ADDRESS = "https://api.weixin.qq.com/cgi-bin/template/set_industry?access_token=ACCESS_TOKEN";

    /**
     * 获取行业模板
     *
     * @param asscess_token
     * @return
     */
    private static String getTemplateMessage(String asscess_token) {
        RestTemplate template = new RestTemplate();

        String url = GET_TEMPLATE_MESSAGE_ADDRESS.replace("ACCESS_TOKEN", asscess_token);

        JSONObject jsonObject = new JSONObject();

        JSONObject result = template.getForObject(url, JSONObject.class);

        System.out.println("===========获取行业===========");
        System.out.println("获取行业 result = " + result);

        return result.toString();
    }

    /**
     * 设置行业的模板
     *
     * @param asscess_token
     * @return
     */
    private static String setTemplateMessage(String asscess_token) {
        RestTemplate template = new RestTemplate();

        String url = SET_TEMPLATE_MESSAGE_ADDRESS.replace("ACCESS_TOKEN", asscess_token);
        //String url = "http://127.0.0.1:8080/jeewx/test";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("industry_id1", "6");
        jsonObject.put("industry_id2", "8");
/*
        Map<String, Object> map = new HashMap<>();
        map.put("industry_id1", "6");
        map.put("industry_id2", "8");
*/
        //这里的第二参数，是将请求的参数放到请求体中

        JSONObject result = template.postForObject(url, jsonObject, JSONObject.class);

        System.out.println(result);
        System.out.println("===========设置行业===========");
        System.out.println("设置行业 result = " + result);

        return result.toString();
    }


    /**
     * http请求方式: POST
     * 获取微信模板消息的接口
     */
    public String template_message = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";

    /**
     * 模板列表
     *
     * @param args
     */
    public static String GET_ALL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";


    public static String SET_ALL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";

    public static String setAllPrivateTemplates(String accessToken) {
        String url = SET_ALL_PRIVATE_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken);
        RestTemplate template = new RestTemplate();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("template_id_short", "TM00015");
        //post请求
        JSONObject result = template.postForObject(url, jsonObject, JSONObject.class);
        System.out.println("result = " + result);

        return null;
    }

    public static String getAllPrivateTemplates(String accessToken) {
        String url = GET_ALL_PRIVATE_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken);
        RestTemplate template = new RestTemplate();

        JSONObject result = template.getForObject(url, JSONObject.class);
        System.out.println("result = " + result);

        return result.toString();
    }


    /**
     * 测试模板信息
     *
     * @return
     */
    public static String getTemplateMessage1(String accessToken, String openId) {

        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openId);   // openid
        jsonObject.put("template_id", "ErsChZQGYAU6fqL-OFTTDEqsfsksgNGGuE5zRhvmXP4");
        //jsonObject.put("template_id", "gh0XPGEomQEvGpZO0QWkVQhRdbFsNjk_xMvHAR3-d2A");
        //jsonObject.put("url", "http://iullor2.utools.club/");

        JSONObject data = new JSONObject();

        JSONObject first = new JSONObject();
        first.put("value", "张医生发来随访问题");
        first.put("color", "#173177");

        JSONObject patientName = new JSONObject();
        patientName.put("value", "张三");
        patientName.put("color", "#FF82AB");

        JSONObject title = new JSONObject();
        title.put("value", "睡眠质量调查");
        title.put("color", "#173177");

        JSONObject interval = new JSONObject();
        interval.put("value", "5天之内");
        interval.put("color", "#173177");

        JSONObject company = new JSONObject();
        company.put("value", "云峰医链");
        company.put("color", "#173177");

        JSONObject location = new JSONObject();
        location.put("value", "数字信息产业园二期");
        location.put("color", "#173177");

        JSONObject test = new JSONObject();
        test.put("value", "测试");
        test.put("color", "#173177");

        JSONObject remark = new JSONObject();
        remark.put("value", "按时睡觉吃饭");
        remark.put("color", "#173177");

        data.put("first", first);

        data.put("patientName", patientName);
        data.put("title", title);
        data.put("interval", interval);
        data.put("company", interval);
        data.put("location", location);
        data.put("test", test);


        data.put("remark", remark);

        jsonObject.put("data", data);

        RestTemplate template = new RestTemplate();

        JSONObject jsonObject1 = template.postForObject(postUrl, jsonObject, JSONObject.class);
        System.out.println("jsonObject1 = " + jsonObject1);

        return null;
    }


    /**
     * 客服消息，发送文本消息
     */
    public static String CUSTOM_SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    /**
     * 发送文本消息
     */
    public static void sendMessage(String accessToken) {
        String url = CUSTOM_SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);

        RestTemplate template = new RestTemplate();
        JSONObject data = new JSONObject();

        //文字消息
        JSONObject text = new JSONObject();
        text.put("content", "hello /:X-)");

        data.put("touser", "o-bZQuD8yubbmhxmLVsCHhGJouXA");
        data.put("msgtype", "text");
        data.put("text", text);


        JSONObject mpnews = new JSONObject();
        mpnews.put("media_id", "");

        JSONObject object = template.postForObject(url, data, JSONObject.class);

        System.out.println("object = " + object);

    }


    /**
     * 获取媒体id的地址
     */
    public static String GET_MEDIA_ID_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    /**
     * 先拿到一个 media_id
     *
     * @param accessToken
     * @return
     */
    public static String getMediaId(String accessToken) {
        String url = GET_MEDIA_ID_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject data = new JSONObject();
        data.put("type", "image");
        data.put("offset", "1");
        data.put("count", "1");

        RestTemplate template = new RestTemplate();
        String resultString = template.postForObject(url, data, String.class);
        System.out.println("resultString = " + resultString);

        Object objString = JSON.parse(resultString);
        JSONObject json = JSONObject.fromObject(objString);
        System.out.println("json = " + json);
        JSONArray item = json.getJSONArray("item");
        JSONObject json1 = JSONObject.fromObject(item.get(0));
        String media_id = (String) json1.get("media_id");
        System.out.println("media_id = " + media_id);

        return media_id;

    }

    /**
     * @param accessToken
     */
    private static String CREATE_QR_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

    public static String sendQrCode(String accessToken) {
        String url = CREATE_QR_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject data = new JSONObject();
        //场景值
        JSONObject actionInfo = new JSONObject();
        JSONObject scene = new JSONObject();
        scene.put("scene_id", 123);

        actionInfo.put("scene", scene);

        data.put("action_name", "QR_LIMIT_SCENE");
        data.put("action_info", actionInfo);

        System.out.println("data = " + data);
        StringBuffer requestStr = new StringBuffer();
        /*requestStr.append("{\"action_info\":{\"scene\":{\"scene_id\":")
                .append(sceneId)
                .append("}},\"action_name\":\"QR_SCENE\",\"expire_seconds\":")
                .append(expireSeconds).append("}");*/


        RestTemplate template = new RestTemplate();
        JSONObject jsonObject = template.postForObject(url, data, JSONObject.class);
        System.out.println("jsonObject = " + jsonObject);

        if (jsonObject.containsKey("ticket")) {
            String ticket = jsonObject.getString("ticket");
            String encode = URLEncoder.encode(ticket);
            return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + encode;
        }
        return null;
    }

    /**
     * 上传图片
     */
    public static String UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";

    /**
     * 上传图片素材
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static String uploadImg(String accessToken, String mediaId) {
        String url = UPLOAD_NEWS_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject article = new JSONObject();
        article.put("title", "测试");


        //"articles": [{
        //    "title": TITLE,
        //            "thumb_media_id": THUMB_MEDIA_ID,
        //            "author": AUTHOR,
        //            "digest": DIGEST,
        //            "show_cover_pic": SHOW_COVER_PIC(0 / 1),
        //            "content": CONTENT,
        //            "content_source_url": CONTENT_SOURCE_URL,
        //            "need_open_comment":1,
        //            "only_fans_can_comment":1

        return null;
    }

    /**
     * 客服发送图片
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static String sendImgMsg(String accessToken, String mediaId) {
        String url = CUSTOM_SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject img = new JSONObject();

        JSONObject image = new JSONObject();
        image.put("media_id", "n8-t1nNsj9gImVjkH7vwHfZHyObP-MmzTpEL0_z1B7pieDnX2s-m8Dr54mTD8vq4");

        img.put("touser", "o-bZQuD8yubbmhxmLVsCHhGJouXA");
        img.put("msgtype", "image");
        img.put("image", image);
//        {
//            MediaId=n8-t1nNsj9gImVjkH7vwHfZHyObP-MmzTpEL0_z1B7pieDnX2s-m8Dr54mTD8vq4,
//                    CreateTime=1572428295,
//                    ToUserName=gh_92a8a8edcfc9,
//                    FromUserName=o-bZQuD8yubbmhxmLVsCHhGJouXA,
//                    MsgType=image,
//                    PicUrl=http: //mmbiz.qpic.cn/mmbiz_jpg/38LI2Nl8urQrE1ugiahBO45u4WbR8k6OnfCPjyLianQiczMGRBZibFJZjtNo46owh3defAtbWaFk4fMbuOwdiaImMcg/0,
//            MsgId=22511760833927639
//        }


//        {
//            "touser":"OPENID",
//             "msgtype":"image",
//             "image":
//            {
//                "media_id":"MEDIA_ID"
//            }
//        }
        System.out.println("img = " + img);
        RestTemplate restTemplate = new RestTemplate();
        JSONObject jsonObject = restTemplate.postForObject(url, image, JSONObject.class);
        System.out.println("jsonObject = " + jsonObject);
        return null;
    }

    public static void main(String[] args) {
        String accessToken = getAccessToken();
        System.out.println("accessToken = " + accessToken);

        /**
         *
         *
         * {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
         * {"action_name":"QR_LIMIT_SCENE","action_info":{"scene":{"scene_str":"test"}}}
         *
         */
        //String templateMessage = getTemplateMessage(accessToken);
        //System.out.println("templateMessage = " + templateMessage);

        //String templateMessage = setTemplateMessage(accessToken);
        //System.out.println("templateMessage = " + templateMessage);

        //String allPrivateTemplates = getAllPrivateTemplates(accessToken);
        //System.out.println("allPrivateTemplates = " + allPrivateTemplates);

        //String allPrivateTemplates = setAllPrivateTemplates(accessToken);
        //System.out.println("setAllPrivateTemplates = " + allPrivateTemplates);

        //传递模板参数，发送模板消息
        //getTemplateMessage1(accessToken, "o-bZQuD8yubbmhxmLVsCHhGJouXA");

        //发送文本消息,用户长时间不和公众号打交道会报 45015错误
        sendMessage(accessToken);

        //发送其他类型的消息，先得获得 MediaId 再传入制定的规则
        //String mediaId = getMediaId(accessToken);

        //sendImgMsg(accessToken,null);


        //发送
        //sendQrCode(accessToken);
    }

}
