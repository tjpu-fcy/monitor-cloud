package com.entity;

public class DeviceMonitorNodeManage {
    private Integer id;

    private String deviceName;

    private Boolean deviceAlarmSet;

    private String deviceSign;

    private String deviceProtocol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Boolean getDeviceAlarmSet() {
        return deviceAlarmSet;
    }

    public void setDeviceAlarmSet(Boolean deviceAlarmSet) {
        this.deviceAlarmSet = deviceAlarmSet;
    }

    public String getDeviceSign() {
        return deviceSign;
    }

    public void setDeviceSign(String deviceSign) {
        this.deviceSign = deviceSign == null ? null : deviceSign.trim();
    }

    public String getDeviceProtocol() {
        return deviceProtocol;
    }

    public void setDeviceProtocol(String deviceProtocol) {
        this.deviceProtocol = deviceProtocol == null ? null : deviceProtocol.trim();
    }
}