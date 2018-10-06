package com.dao;

import com.entity.DeviceMonitorNodeManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeviceMonitorNodeManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceMonitorNodeManage record);

    int insertSelective(DeviceMonitorNodeManage record);

    DeviceMonitorNodeManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceMonitorNodeManage record);

    int updateByPrimaryKey(DeviceMonitorNodeManage record);

    List<DeviceMonitorNodeManage> selectByAll();
}