package com.common.weixin;

import org.springframework.beans.factory.annotation.Value;

public class WeixinCommon {


    private static volatile   String accessToken ;    // 微信企业号的AccessToken, 由corpID和secret动态生成，两小时到期

    public static String getAccessToken() {
        return accessToken;
    }

    public synchronized static void setAccessToken(String accessToken) {
        WeixinCommon.accessToken = accessToken;
    }


}
