package com.device;



import java.util.HashMap;
import java.util.Map;

//设备节点map集合
public class DeviceManage {


   private static Map<String, Map<String, Object >> deviceManageMap  = new HashMap<String,Map<String,Object>>();
   public static void addMap(String key , Map<String, Object> map){
       deviceManageMap.put(key, map) ;
   }

    public static Map<String, Object> getMap(String key ){
      return  deviceManageMap.get(key) ;
    }


}
