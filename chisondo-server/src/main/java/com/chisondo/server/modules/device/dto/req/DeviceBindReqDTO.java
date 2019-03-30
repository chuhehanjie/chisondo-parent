package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

/**
 * 设备绑定请求DTO
 */
public class DeviceBindReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String deviceId; // 设备ID
    private String phoneNum; // 手机号码	设备绑定的手机号码
    private Integer companyId; // 所属企业id	0-泉笙道，1-湘丰集团，2-静硒园,345….,默认-1，表示还没有绑定企业，第一次时修改，不为-1时，不允许再修改
    private String longitude; // 经度	手机定位
    private String latitude; // 维度
    private String province; // 省
    private String city; // 市
    private String district; // 区
    private String detaddress; // 详细地址
    private String passwd; // 设备密码	需md5加密，默认密码（123456）

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetaddress() {
        return detaddress;
    }

    public void setDetaddress(String detaddress) {
        this.detaddress = detaddress;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
