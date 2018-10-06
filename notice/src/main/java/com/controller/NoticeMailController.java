package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.CommonInfo;
import com.common.QueueData;
import com.common.mail.SendMail;
import com.dao.NoticeSmsMapper;
import com.entity.NoticeSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @version 1.0.0
 * @author  fcy
 *
 */
@Controller
@RequestMapping("/mail")
public class NoticeMailController {

    @Autowired
    private SendMail sendMail;


    //邮件发送rest接口
    @ResponseBody
    @RequestMapping(value="/sendMail", method=RequestMethod.POST)
    public Map sendMail(@RequestBody String jsonData) {

        JSONObject jsonObject = JSON.parseObject(jsonData);
        String context= jsonObject.getString("context");//内容
        String name= jsonObject.getString("name");//名字
        String account= jsonObject.getString("account");//账号

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            sendMail.sendSimpleEmail(account, name,context);
            map.put("status", true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            return map;
        }
    }

}