package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.redis.RedisUtil;
import com.entity.User;
import com.entity.UserProfile;
import com.feign.NoticeService;
import com.jwt.JwtToken;
import com.md5.md5Util;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/forgetPassword")
public class ForgetPasswordController {


    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RedisUtil redisUtil;


    //密码重置
    @RequestMapping("/reset.do")
    @ResponseBody
    public Map<String,Object> reset(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String email = jsonObject.getString("email");
        String password= jsonObject.getString("password");
        UserProfile userProfile = userService.findByEmail(email);
        User user = userService.findById(userProfile.getUserId());
        //密码加盐
        user.setUserPassword(md5Util.generate(password));
        try {
            userService.resetUserAccountPassword(user);
            map.put("status", true);
            return map;
        } catch (Exception e) {
             e.printStackTrace();
            map.put("status", false);
            return map;
        }

    }

    //给邮箱发送验证码
    @RequestMapping("/getCheckCode.do")
    @ResponseBody
    public Map<String,Object> getCheckCode(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String email= jsonObject.getString("email");
        UserProfile userProfile = userService.findByEmail(email);
        if(userProfile == null) {
            map.put("status", "noMail");
            return map;
        }
        try {
            //生成6位随机数
            String checkCode = Integer.toString((int)((Math.random()*9+1)*100000));
            //生成redis key
            String redisKey = "emailCheck:" + email;
            //邮箱验证码保存在redis,过期时间10分钟
            redisUtil.stringSetExpire(redisKey, checkCode,600 );
            Map<String, Object> mailMap = new HashMap<String, Object>();
            mailMap.put("context", "你好，验证码为" + checkCode + "，十分钟内有效，请不要泄露！" );
            mailMap.put("name", "找回密码验证");
            mailMap.put("account", email);
            noticeService.sendMail(mailMap);
            map.put("status", "success");
            return map;
        } catch (Exception e) {
             e.printStackTrace();
            map.put("status", "failed");
            return map;
        }

    }

    //验证验证码是否正确
    @RequestMapping("/verifyCheckCode.do")
    @ResponseBody
    public Map<String,Object> verifyCheckCode(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String email= jsonObject.getString("email");
        String checkCode= jsonObject.getString("checkCode");

        try {
            //生成redis key
            String redisKey = "emailCheck:" + email;
            //key不存在，说明验证码过期
            if(redisUtil.hasKey(redisKey).equals(false)){
                map.put("status", "notExist");
                return map;
            }
            String value = (String)redisUtil.stringGet(redisKey);
            //验证通过
            if(value.equals(checkCode)) {
                map.put("status", "success");
                redisUtil.deleteKey(redisKey); //验证通过后删除key
                return map;
            } else {
                map.put("status", "codeError");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "failed");
            return map;
        }

    }

}
