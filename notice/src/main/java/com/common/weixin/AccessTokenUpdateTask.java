package com.common.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.CommonInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimerTask;

//微信接口AccessToken的定时更新任务
// AccessToken每两小时到期，这里设置每一小时更新一次（应用启动的时候更新一次）

@Component
public class AccessTokenUpdateTask {

    private static  String corpId ;    // 微信企业号的CorpID，是企业号的标识，每个企业号拥有一个唯一的CorpID

    private static  String secret ;    // 微信企业号的secret, 一个应用1个secret

    @Value("${weixin.corpId}")
    public  void setCorpId(String corpId) {
        AccessTokenUpdateTask.corpId = corpId;
    }

    @Value("${weixin.secret}")
    public  void setSecret(String secret) {
        AccessTokenUpdateTask.secret = secret;
    }

    // 每小时执行
    @Scheduled(fixedRate = 60*60*1000)
    public void updateToken(){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId  + "&corpsecret=" + secret;
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet2 = new HttpGet(url);
        String srtResult = null;
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpCilent.execute(httpGet2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            try {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                JSONObject json = JSON.parseObject(srtResult);
                String accessToken = json.getString("access_token");
                WeixinCommon.setAccessToken(accessToken);
                 //  System.out.println("更新token");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
