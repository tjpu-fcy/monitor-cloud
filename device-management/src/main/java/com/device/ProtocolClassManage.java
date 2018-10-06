package com.device;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//所有设备通信协议的class对象的管理
public class ProtocolClassManage {

    //创建一个map
    //key为设备通信协议所对应的class文件的名字前缀，是唯一的
    //value为class文件被jvm加载后的class对象，每一个设备节点都对应着由class对象创建的一个实例
    private static Map<String, Class<?> > classManageMap = new ConcurrentHashMap<String, Class<?>>();

    public static void addClassManage(String key, Class cls){
        classManageMap.put(key, cls);
    }

    public static Class<?> getClassManage(String key){
       return classManageMap.get(key);
    }
}
