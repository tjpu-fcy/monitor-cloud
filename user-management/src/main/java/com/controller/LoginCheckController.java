package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.redis.RedisUtil;
import com.entity.User;
import com.feign.NoticeService;
import com.jwt.JwtToken;
import com.md5.md5Util;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/loginCheck")
public class LoginCheckController {


    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private NoticeService noticeService;

    //用户登录
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,String> login(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String userAccount = jsonObject.getString("userAccount");
        String password = jsonObject.getString("password");
        Map<String,String> map =new HashMap<String,String>();
        User user = userService.findByUserAccount(userAccount);
        if (user==null) {
            map.put("status", "noUser");
        } else {
            Boolean result = md5Util.verify(password, user.getUserPassword());
            if (result.equals(true)) {
                try {
                    //创建token，token的生成需要用户账户名，redisKey(基于用户表+用户id主键生成）
                    String redisKey = "user&user_profile:"+ Integer.toString(user.getId());
                    String jwt = JwtToken.createJWT(userAccount, redisKey);
                    map.put("jwtKey", jwt);
                    map.put("status", "success");
                    //token保存在redis,过期时间5分钟
                    redisUtil.stringSetExpire(redisKey, jwt,300 );
                    Map<String, Object> weixinMap = new HashMap<String, Object>();
                    weixinMap.put("context", "用户"+userAccount+"已经登陆。");
                    weixinMap.put("name", userAccount);
                    weixinMap.put("account", "fuchenyang");
                    noticeService.sendWeixin(weixinMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                map.put("status", "failed");
            }
        }
        return map;

    }



    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request) {

        return "index";
    }
}
