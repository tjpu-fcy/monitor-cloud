package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entity.DeviceMonitorNodeManage;
import com.dao.DeviceMonitorNodeManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @version 1.0.0
 * @blog http://blog.fdway.com
 *
 */
@Controller
@RequestMapping("/deviceMonitor")
public class IndexController {



    @Autowired
    private DeviceMonitorNodeManageMapper deviceMonitorNodeManageMapper;





    @RequestMapping("/manage")
    public String notice_sms(Model model) {

        return "device_monitor_manage";
    }

    @ResponseBody
    @RequestMapping(value="/manage/newDevice", method=RequestMethod.POST)
    public Map newDevice(@RequestBody String jsonData) {

        JSONObject jsonObject = JSON.parseObject(jsonData);
        String deviceName= jsonObject.getString("deviceName");
        String deviceSign= jsonObject.getString("deviceSign");
        String deviceProtocol= jsonObject.getString("deviceProtocol");
        String deviceSet= jsonObject.getString("deviceSet");
//        System.out.println(deviceName);
//        System.out.println(deviceSign);
//        System.out.println(deviceProtocol);
//        System.out.println(deviceSet);
        DeviceMonitorNodeManage deviceMonitorNodeManage = new DeviceMonitorNodeManage();
        if("true".equals(deviceSet)){
            deviceMonitorNodeManage.setDeviceAlarmSet(true);
        }else {
            deviceMonitorNodeManage.setDeviceAlarmSet(false);
        }
        deviceMonitorNodeManage.setDeviceName(deviceName);
        deviceMonitorNodeManage.setDeviceProtocol(deviceProtocol);
        deviceMonitorNodeManage.setDeviceSign(deviceSign);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            deviceMonitorNodeManageMapper.insertSelective(deviceMonitorNodeManage);
            map.put("status", true);
            return map;
        } catch (Exception e) {
            map.put("status", false);
            return map;
        }
    }


//    //批量删除短信消息
//    @ResponseBody
//    @RequestMapping(value="/weixin/deleteList", method=RequestMethod.POST)
//    public Map deleteWeixinList(@RequestBody String jsonData) {
//
//        JSONObject jsonObject = JSON.parseObject(jsonData);
//        String deleteIds= jsonObject.getString("deleteIds");
//        String[] idArray = deleteIds.split(",");
//        int array[] = new int[idArray.length];
//        for(int i=0;i<idArray.length;i++) {
//            array[i] = Integer.parseInt(idArray[i]);
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            noticeWeixinMapper.deleteByPrimaryKeys(array);
//            map.put("status", true);
//            return map;
//        } catch (Exception e) {
//            map.put("status", false);
//            return map;
//        }
//    }


//
//    //短信发送rest接口
//    @ResponseBody
//    @RequestMapping(value="/sms/sendSms", method=RequestMethod.POST)
//    public Map sendSms(@RequestBody String jsonData) {
//
//        JSONObject jsonObject = JSON.parseObject(jsonData);
//        String context= jsonObject.getString("context");//内容
//        String name= jsonObject.getString("name");//名字
//        String account= jsonObject.getString("account");//账号
//        QueueData queueData = new QueueData();
//        queueData.setNumber(1);
//        queueData.setContext(context);
//        queueData.setName(name);
//        queueData.setAccount(account);
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            CommonInfo.NoticeQueue.put(queueData);
//            map.put("status", true);
//            return map;
//        } catch (InterruptedException e) {
//            map.put("status", false);
//            return map;
//        }
//
//    }





}