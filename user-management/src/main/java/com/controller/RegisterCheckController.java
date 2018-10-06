package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.entity.UserProfile;
import com.feign.NoticeService;
import com.md5.md5Util;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//新用户注册
@Controller
@RequestMapping(value = "/registerCheck")
public class RegisterCheckController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;




    //用户注册校验
    @RequestMapping("/check.do")
    @ResponseBody
    public Map<String,String> check(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map = new HashMap<String,String>();
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String userAccount= jsonObject.getString("userAccount");
        String password= jsonObject.getString("password");
        String email= jsonObject.getString("email");
        //验证账户是否已经存在
        User checkUser = userService.findByUserAccount(userAccount);
        if (checkUser != null) {
            map.put("status", "existUser");
            return map;
        }

        //验证邮箱是否已经被注册
        UserProfile checkUserProfile = userService.findByEmail(email);
        if (checkUserProfile != null) {
            map.put("status", "existMail");
            return map;
        }
        User user = new User();
        user.setUserAccount(userAccount);
        //密码加盐
        user.setUserPassword(md5Util.generate(password));
        UserProfile userProfile = new UserProfile();
        userProfile.setUserEmail(email);
        try {
            userService.insertRegisterUser(user, userProfile);
            map.put("status", "success");
 //           Map<String, Object> smsMap = new HashMap<String, Object>();
//            smsMap.put("context", "注册成功短信通知");
//            smsMap.put("name", "用户注册");
//            smsMap.put("account", "15658025326");
//            noticeService.sendSms(smsMap);

            Map<String, Object> weixinMap = new HashMap<String, Object>();
            weixinMap.put("context", "用户"+userAccount+"已经注册成功。");
            weixinMap.put("name", userAccount);
            weixinMap.put("account", "fuchenyang");
            noticeService.sendWeixin(weixinMap);

            Map<String, Object> mailMap = new HashMap<String, Object>();
            mailMap.put("context", "用户"+userAccount+"你好，你已经注册成功。");
            mailMap.put("name", "注册成功邮件通知");
            mailMap.put("account", email);
            noticeService.sendMail(mailMap);
            return map;
        } catch (Exception e) {
             e.printStackTrace();
            map.put("status", "failed");
            return map;
        }
    }


}
