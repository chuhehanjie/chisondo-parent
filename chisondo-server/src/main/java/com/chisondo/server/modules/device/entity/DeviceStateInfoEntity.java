package com.chisondo.server.modules.device.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class DeviceStateInfoEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer deviceId;
	//
	private Integer onlineState;
	//
	private String deviceStateInfo;
	//
	private Integer connectState;
	//
	private Date updateTime;
	//
	private Date lastConnTime;
	//
	private Date lastValTime;
	//
	private String clientIpAddress;
	//
	private Double longitude;
	//
	private Double latitude;
	//
	private String province;
	//
	private String city;
	//
	private String district;
	//
	private String address;
	//设定加热温度
	private Integer makeTemp;
	//即时温度值
	private Integer temp;
	//是否保温,0-未保温 1-保温中
	private Integer warm;
	//浓淡指示灯状态,1:浓; 2:中; 3:淡
	private Integer density;
	//设定出水量,(单位：毫升ml)
	private Integer waterlv;
	//设定沏茶时间,0 - 不浸泡，1~600  浸泡时间(单位:秒)
	private Integer makeDura;
	//剩余工作时间,面板显示剩余时间
	private Integer reamin;
	//即时缺茶状态,1:缺茶
	private Integer tea;
	//即时缺水状态,1:缺水
	private Integer water;
	//沏茶类型,0-茶谱沏茶；1-普通沏茶；2-面板操作
	private Integer makeType;
	//茶类ID
	private Integer teaSortId;
	//茶类名称
	private String teaSortName;
	//茶谱名称
	private String chapuName;
	//茶谱图像
	private String chapuImage;
	//茶谱总泡数
	private Integer chapuMakeTimes;
	//第几泡,>0：当前正在进行的是第几泡；-1：没有正在使用的茶谱；0：已完成茶谱最大泡数，下一步开始第1泡；999：茶谱正常结束
	private Integer index;
	// 即时沏茶状态	0:没有工作, 1: 沏茶, 2: 洗茶，3：烧水
	private Integer work;
	// 使用的茶谱ID	没有在使用茶谱返回0
	private Integer chapuId;

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}
	public void setOnlineState(Integer onlineState) {
		this.onlineState = onlineState;
	}

	public Integer getOnlineState() {
		return onlineState;
	}
	public void setDeviceStateInfo(String deviceStateInfo) {
		this.deviceStateInfo = deviceStateInfo;
	}

	public String getDeviceStateInfo() {
		return deviceStateInfo;
	}
	public void setConnectState(Integer connectState) {
		this.connectState = connectState;
	}

	public Integer getConnectState() {
		return connectState;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setLastConnTime(Date lastConnTime) {
		this.lastConnTime = lastConnTime;
	}

	public Date getLastConnTime() {
		return lastConnTime;
	}
	public void setLastValTime(Date lastValTime) {
		this.lastValTime = lastValTime;
	}

	public Date getLastValTime() {
		return lastValTime;
	}
	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvince() {
		return province;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict() {
		return district;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
	public void setMakeTemp(Integer makeTemp) {
		this.makeTemp = makeTemp;
	}

	public Integer getMakeTemp() {
		return makeTemp;
	}
	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getTemp() {
		return temp;
	}
	public void setWarm(Integer warm) {
		this.warm = warm;
	}

	public Integer getWarm() {
		return warm;
	}
	public void setDensity(Integer density) {
		this.density = density;
	}

	public Integer getDensity() {
		return density;
	}
	public void setWaterlv(Integer waterlv) {
		this.waterlv = waterlv;
	}

	public Integer getWaterlv() {
		return waterlv;
	}
	public void setMakeDura(Integer makeDura) {
		this.makeDura = makeDura;
	}

	public Integer getMakeDura() {
		return makeDura;
	}
	public void setReamin(Integer reamin) {
		this.reamin = reamin;
	}

	public Integer getReamin() {
		return reamin;
	}
	public void setTea(Integer tea) {
		this.tea = tea;
	}

	public Integer getTea() {
		return tea;
	}
	public void setWater(Integer water) {
		this.water = water;
	}

	public Integer getWater() {
		return water;
	}
	public void setMakeType(Integer makeType) {
		this.makeType = makeType;
	}

	public Integer getMakeType() {
		return makeType;
	}
	public void setTeaSortId(Integer teaSortId) {
		this.teaSortId = teaSortId;
	}

	public Integer getTeaSortId() {
		return teaSortId;
	}
	public void setTeaSortName(String teaSortName) {
		this.teaSortName = teaSortName;
	}

	public String getTeaSortName() {
		return teaSortName;
	}
	public void setChapuName(String chapuName) {
		this.chapuName = chapuName;
	}

	public String getChapuName() {
		return chapuName;
	}
	public void setChapuImage(String chapuImage) {
		this.chapuImage = chapuImage;
	}

	public String getChapuImage() {
		return chapuImage;
	}
	public void setChapuMakeTimes(Integer chapuMakeTimes) {
		this.chapuMakeTimes = chapuMakeTimes;
	}

	public Integer getChapuMakeTimes() {
		return chapuMakeTimes;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}

	public Integer getWork() {
		return work;
	}

	public void setWork(Integer work) {
		this.work = work;
	}

	public Integer getChapuId() {
		return null == chapuId ? 0 : chapuId;
	}

	public void setChapuId(Integer chapuId) {
		this.chapuId = chapuId;
	}
}
