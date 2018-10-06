package com.common.weixin;



import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendWeixinMessage {


	private static  String agentid ;    // 企业应用的id

	@Value("${weixin.agentid}")
	public  void setAgentid(String agentid) {
		SendWeixinMessage.agentid = agentid;
	}


	public  Boolean send( String account, String name, String content)  {

		String accessToken = WeixinCommon.getAccessToken();
		Boolean status = false;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
				+ accessToken;
		HttpPost httpPost = new HttpPost(requestUrl);
		httpPost.setHeader("Content-Type","application/json");  //
		String postJson = "{\"agentid\":\"%s\",\"touser\":\"%s\",\"toparty\":\"[%s]\",\"totag\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"},\"safe\":\"0\"}";

		String toparty = "";
		String totag = "";
		String outputStr = String.format(postJson, agentid, account, toparty,
				totag, content);
		httpPost.setEntity(new StringEntity(outputStr, ContentType.create("application/json", "utf-8")));
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()==200){
			//	System.out.println("微信发送成功。"+"用户:"+name+"。内容:"+content);
				status = true;
			} else{
				System.out.println("微信发送失败"+response.getStatusLine().getStatusCode());
				status = false;
			}
		} catch (IOException e) {
			System.out.println("微信发送失败");
			status = false;
		}finally {
			try {
				httpClient.close();//释放资源
			} catch (IOException e) {
				e.printStackTrace();

			}
			return status;
		}

	}


	
}
