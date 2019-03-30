package com.chisondo.server.modules.device.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class DeviceColorEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer colorId;
	//
	private Integer deviceTypeId;
	//
	private String colorRgb;
	//
	private String colorName;
	//
	private String imageLarge;
	//
	private String imageSmall;

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public Integer getColorId() {
		return colorId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setColorRgb(String colorRgb) {
		this.colorRgb = colorRgb;
	}

	public String getColorRgb() {
		return colorRgb;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorName() {
		return colorName;
	}
	public void setImageLarge(String imageLarge) {
		this.imageLarge = imageLarge;
	}

	public String getImageLarge() {
		return imageLarge;
	}
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageSmall() {
		return imageSmall;
	}
}
