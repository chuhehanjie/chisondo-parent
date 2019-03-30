package com.chisondo.server.modules.tea.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class TeaStatisticsEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String userId;
	//
	private Integer userNum;
	//
	private Integer amountTotal;
	//
	private Integer amountDaily;
	//
	private String amountDayList;
	//
	private String sortPercent;
	//
	private String sortFavAm;
	//
	private String sortFavPm;
	//
	private String sortFavNight;
	//
	private Integer myContDays;
	//
	private Integer myBeatPercent;
	//
	private Integer myExceedDays;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Integer getUserNum() {
		return userNum;
	}
	public void setAmountTotal(Integer amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Integer getAmountTotal() {
		return amountTotal;
	}
	public void setAmountDaily(Integer amountDaily) {
		this.amountDaily = amountDaily;
	}

	public Integer getAmountDaily() {
		return amountDaily;
	}
	public void setAmountDayList(String amountDayList) {
		this.amountDayList = amountDayList;
	}

	public String getAmountDayList() {
		return amountDayList;
	}
	public void setSortPercent(String sortPercent) {
		this.sortPercent = sortPercent;
	}

	public String getSortPercent() {
		return sortPercent;
	}
	public void setSortFavAm(String sortFavAm) {
		this.sortFavAm = sortFavAm;
	}

	public String getSortFavAm() {
		return sortFavAm;
	}
	public void setSortFavPm(String sortFavPm) {
		this.sortFavPm = sortFavPm;
	}

	public String getSortFavPm() {
		return sortFavPm;
	}
	public void setSortFavNight(String sortFavNight) {
		this.sortFavNight = sortFavNight;
	}

	public String getSortFavNight() {
		return sortFavNight;
	}
	public void setMyContDays(Integer myContDays) {
		this.myContDays = myContDays;
	}

	public Integer getMyContDays() {
		return myContDays;
	}
	public void setMyBeatPercent(Integer myBeatPercent) {
		this.myBeatPercent = myBeatPercent;
	}

	public Integer getMyBeatPercent() {
		return myBeatPercent;
	}
	public void setMyExceedDays(Integer myExceedDays) {
		this.myExceedDays = myExceedDays;
	}

	public Integer getMyExceedDays() {
		return myExceedDays;
	}
}
