package com.common;

import com.common.sms.SendSmsMessage;
import com.common.weixin.SendWeixinMessage;
import com.dao.NoticeSmsMapper;
import com.dao.NoticeWeixinMapper;
import com.entity.NoticeSms;
import com.entity.NoticeWeixin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//队列消费者

@Component
public class QueueConsume extends Thread{

	@Autowired
	private  NoticeWeixinMapper noticeWeixinMapper ;
	@Autowired
	private  NoticeSmsMapper noticeSmsMapper ;
	@Autowired
	private  SendSmsMessage sendSmsMessage ;
	@Autowired
	private  SendWeixinMessage sendWeixinMessage ;

	@Override
	public void run() {
		while(true) {
			try {
				QueueData queueData = CommonInfo.NoticeQueue.take();
				CommonInfo.cachedThread.execute((new Runnable() {
					@Override
					public void run() {
						//为短信消息
						if (queueData.getNumber() == 1) {
							if (queueData.getAccount() != null && queueData.getAccount().length() > 0) {
								Boolean status1 = sendSmsMessage.send(queueData.getAccount(), queueData.getName(), queueData.getContext());
								//如果发送成功，就保存到数据库
								if (status1 == true) {
									NoticeSms noticeSms = new NoticeSms();
									noticeSms.setUserName(queueData.getName());
									noticeSms.setSmsNumber(queueData.getAccount());
									noticeSms.setContext(queueData.getContext());
									noticeSmsMapper.insert(noticeSms);

								}
							}
						}

						//为微信消息
						else if (queueData.getNumber() == 2) {
							if (queueData.getAccount() != null && queueData.getAccount().length() > 0) {
								Boolean status2 = sendWeixinMessage.send(queueData.getAccount(), queueData.getName(), queueData.getContext());
								//如果发送成功，就保存到数据库
								if (status2 == true) {
									NoticeWeixin noticeWeixin = new NoticeWeixin();
									noticeWeixin.setContext(queueData.getContext());
									noticeWeixin.setUserName(queueData.getName());
									noticeWeixin.setWeixinNumber(queueData.getAccount());
									noticeWeixinMapper.insert(noticeWeixin);
								}
							}

						}
					}
				}));

				//  Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
