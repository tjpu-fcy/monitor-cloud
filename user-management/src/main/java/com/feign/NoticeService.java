package com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@FeignClient(value = "notice")
public interface  NoticeService {
    @RequestMapping(value="/sms/sendSms",method = RequestMethod.POST)
    public Map<String,Object> sendSms(Map<String,Object> map);

    @RequestMapping(value="/mail/sendMail",method = RequestMethod.POST)
    public Map<String,Object> sendMail(Map<String,Object> map);

    @RequestMapping(value="/weixin/sendWeixin",method = RequestMethod.POST)
    public Map<String,Object> sendWeixin(Map<String,Object> map);

}
