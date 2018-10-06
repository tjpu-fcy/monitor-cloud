package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.CommonInfo;
import com.common.QueueData;
import com.dao.NoticeSmsMapper;
import com.entity.NoticeSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/sms")
public class NoticeSmsController {



    @Autowired
    private NoticeSmsMapper noticeSmsMapper;



    //查询某时间段内的短信消息
    @ResponseBody
    @RequestMapping(value="/checkList", method=RequestMethod.POST)
    public List<NoticeSms> checkSmsList(@RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        String startTime= jsonObject.getString("startTime");
        String endTime= jsonObject.getString("endTime");
        List<NoticeSms> list = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        if((startTime != null && startTime.length() >0)&&(endTime != null && endTime.length() >0)) {
            try {
                list = noticeSmsMapper.selectByTime(sdf.parse(startTime), sdf.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return list;
        } else {
            return null;
        }
    }


    //批量删除短信消息
    @ResponseBody
    @RequestMapping(value="/deleteList", method=RequestMethod.POST)
    public Map deleteSmsList(@RequestBody String jsonData) {

        JSONObject jsonObject = JSON.parseObject(jsonData);
        String deleteIds= jsonObject.getString("deleteIds");
        String[] idArray = deleteIds.split(",");
        int array[] = new int[idArray.length];
        for(int i=0;i<idArray.length;i++) {
            array[i] = Integer.parseInt(idArray[i]);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            noticeSmsMapper.deleteByPrimaryKeys(array);
            map.put("status", true);
            return map;
        } catch (Exception e) {
            map.put("status", false);
            return map;
        }
    }



    //短信发送rest接口
    @ResponseBody
    @RequestMapping(value="/sendSms", method=RequestMethod.POST)
    public Map sendSms(@RequestBody String jsonData) {

        JSONObject jsonObject = JSON.parseObject(jsonData);
        String context= jsonObject.getString("context");//内容
        String name= jsonObject.getString("name");//名字
        String account= jsonObject.getString("account");//账号
        QueueData queueData = new QueueData();
        queueData.setNumber(1);
        queueData.setContext(context);
        queueData.setName(name);
        queueData.setAccount(account);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            CommonInfo.NoticeQueue.put(queueData);
            map.put("status", true);
            return map;
        } catch (InterruptedException e) {
            map.put("status", false);
            return map;
        }
    }

}