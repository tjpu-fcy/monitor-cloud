package com.common.sms;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendSmsMessage {

	private static  String accessKeyId;        //你的阿里云accessKeyId

	private static  String accessKeySecret ;   //你的阿里云accessKeySecret

	private static String signNamerom;       // 短信签名

	private static  String templateCode ;    // 短信模板

	@Value("${aliyun.sms.accessKeyId}")
	public void setAccessKeyId(String accessKeyId) {
		SendSmsMessage.accessKeyId = accessKeyId;
	}

	@Value("${aliyun.sms.accessKeySecret}")
	public void setAccessKeySecret(String accessKeySecret) {
		SendSmsMessage.accessKeySecret = accessKeySecret;
	}

	@Value("${aliyun.sms.signNamerom}")
	public void setSignNamerom(String signNamerom) {
		SendSmsMessage.signNamerom = signNamerom;
	}

	@Value("${aliyun.sms.templateCode}")
	public void setTemplateCode(String templateCode) {
		SendSmsMessage.templateCode = templateCode;
	}


	public Boolean send(String phoneNumber , String name, String context ) {

		// 以下为发送程序，用户无需改动
		try {
			//设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		    //初始化ascClient需要的几个参数
		    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		    //替换成你的AK
		    //初始化ascClient,暂时不支持多region
		    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		    accessKeySecret);
		    try {
				DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			} catch (ClientException e) {
		    	e.printStackTrace();
		    	return false;

			}
		    IAcsClient acsClient = new DefaultAcsClient(profile);
		     //组装请求对象
		     SendSmsRequest request = new SendSmsRequest();
		     //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		     request.setPhoneNumbers(phoneNumber);
		     //必填:短信签名-可在短信控制台中找到
		     request.setSignName(signNamerom);
		     //必填:短信模板-可在短信控制台中找到
		     request.setTemplateCode(templateCode);
		     //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			String templateParam = "{\"device\":\""+name+"\", \"alarm\":\""+context+"\"}"; // 模板中的变量替换JSON串

			request.setTemplateParam(templateParam);
		     //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
		     //request.setSmsUpExtendCode("90997");
		     //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		    // request.setOutId(outId);
		    //请求失败这里会抛ClientException异常
		    SendSmsResponse sendSmsResponse = null;
			try {
				sendSmsResponse = acsClient.getAcsResponse(request);
			} catch (ServerException e) {
				e.printStackTrace();
				return false;
			} catch (ClientException e) {
				e.printStackTrace();
				return false;
			}
		    if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		    //	System.out.println("发送成功");
				return true;
		    }else {
				System.out.println("发送失败"+sendSmsResponse.getCode()+" "+sendSmsResponse.getMessage());
				return false;
			}
		} catch (Exception e) {
			System.out.println("发送失败");
			return false;
		}
	}

	
}
