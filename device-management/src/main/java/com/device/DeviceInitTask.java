package com.device;

import com.dao.DeviceMonitorNodeManageMapper;
import com.entity.DeviceMonitorNodeManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//设备初始化加载任务
@Component
public class DeviceInitTask extends Thread {

    @Autowired
	private  DeviceMonitorNodeManageMapper deviceMonitorNodeManageMapper;


	@Override
    public void run() {

              //从数据库中获取全部设备列表，并生成设备管理map集合
                List<DeviceMonitorNodeManage> list = deviceMonitorNodeManageMapper.selectByAll();
                for(DeviceMonitorNodeManage deviceMonitorNodeManage : list){
                    Map<String, Object > map = new HashMap<String, Object >();
                    map.put("name",deviceMonitorNodeManage.getDeviceName());
                    map.put("protocolId",deviceMonitorNodeManage.getDeviceProtocol());
                    map.put("protocolClass",ProtocolClassManage.getClassManage(deviceMonitorNodeManage.getDeviceProtocol()));
                    try {
                        map.put("protocol",ProtocolClassManage.getClassManage(deviceMonitorNodeManage.getDeviceProtocol()).newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    System.out.println(deviceMonitorNodeManage.getDeviceSign());
                    DeviceManage.addMap(deviceMonitorNodeManage.getDeviceSign(),map);
                }

	}
}
