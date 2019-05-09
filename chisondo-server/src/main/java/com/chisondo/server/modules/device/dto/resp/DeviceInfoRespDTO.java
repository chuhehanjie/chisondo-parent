package com.chisondo.server.modules.device.dto.resp;

public class DeviceInfoRespDTO {
    private Integer	deviceId; // 	设备ID
    private String	deviceName; // 	设备名称
    private String	deviceDesc; // 	设备描述
    private Integer	connStatus; // 	连接状态	0:未连接, 非0:已有用户连接上
    private Integer	onlineStatus; // 	在线状态	0不在线，1在线
    private Integer	defaultFlag; // 	是否默认设备	0不是，1是
    private Integer	reserveNum; // 	有效预约个数
    private String	ipAddress; // 	设备IP
    private String	deviceImg; // 	设备图片
    private String	province; // 	省
    private String	city; // 	市
    private String	district; // 	县/区
    private String	address; // 	详细地址
    private int	companyId; // 	所属企业id
    private String	companyName; // 	所属企业名称	0-泉笙道，1-湘丰集团，2-静硒园,345….,默认 0泉笙道
    private String	longitude; // 	GPS经度	设备 经度
    private String	latitude; // 	GPS纬度	设备 纬度
    private Integer deviceType; // 0，Wi-Fi版设备；1，2G版设备；
    private String WiFiSSID; // 当Wi-Fi在线时 返回
    private String newDeviceId;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public Integer getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(Integer connStatus) {
        this.connStatus = connStatus;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Integer getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(Integer reserveNum) {
        this.reserveNum = reserveNum;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceImg() {
        return deviceImg;
    }

    public void setDeviceImg(String deviceImg) {
        this.deviceImg = deviceImg;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getWiFiSSID() {
        return WiFiSSID;
    }

    public void setWiFiSSID(String wiFiSSID) {
        WiFiSSID = wiFiSSID;
    }

    public String getNewDeviceId() {
        return newDeviceId;
    }

    public void setNewDeviceId(String newDeviceId) {
        this.newDeviceId = newDeviceId;
    }
}
