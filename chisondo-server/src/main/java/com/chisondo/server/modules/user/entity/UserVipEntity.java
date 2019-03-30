package com.chisondo.server.modules.user.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员信息
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class UserVipEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//会员标识
	private Long memberId;
	//会员昵称
	private String vipNickname;
	//会员手机号码
	private String phone;
	//用户登陆密码
	private String vipPwd;
	//用户真实姓名
	private String vipName;
	//邮箱
	private String vipEmail;
	//会员性别：1-男;0-女
	private Integer sex;
	//图像路径
	private String vipHeadImg;
	//个性签名
	private String personalizedSignature;
	//可用标识:0-可用，1-不可用
	private Integer useTag;
	//
	private String rsrvStr1;
	//
	private String rsrvStr3;
	//
	private String rsrvStr2;
	//
	private Integer rsrvNum1;
	//
	private Integer rsrvNum2;
	//
	private Integer rsrvNum3;
	//QQID
	private String qqId;
	//QQ昵称
	private String qqNickname;
	//微信ID
	private String wechatId;
	//微信昵称
	private String wechatNickname;
	//微博ID
	private String microblogId;
	//微博昵称
	private String microblogNickname;
	//
	private Date regTime;
	//
	private Date birthday;
	//
	private String backImg;
	//
	private String nicknameJianpin;
	//
	private String nameFirstJianpin;
	//
	private Date loginTime;
	//
	private Integer isTalent;
	//
	private Date talentSettime;
	//
	private String wechatUnionid;
	//
	private String chaqinOpenid;
	//
	private String appMarketName;
	//
	private Integer isFirstLogin4bind;
	//
	private Integer recomUserId;
	//注册来源类型，包括微信、APP、分销、邀请、第三方
	private String regSrcType;
	//注册来源，包括茶亲社区、茶亲时光、泉笙道、iOS、各应用市场、分销二维码数据、邀请人ID、第三方名称
	private String regSrc;

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMemberId() {
		return memberId;
	}
	public void setVipNickname(String vipNickname) {
		this.vipNickname = vipNickname;
	}

	public String getVipNickname() {
		return vipNickname;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}
	public void setVipPwd(String vipPwd) {
		this.vipPwd = vipPwd;
	}

	public String getVipPwd() {
		return vipPwd;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getVipName() {
		return vipName;
	}
	public void setVipEmail(String vipEmail) {
		this.vipEmail = vipEmail;
	}

	public String getVipEmail() {
		return vipEmail;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return sex;
	}
	public void setVipHeadImg(String vipHeadImg) {
		this.vipHeadImg = vipHeadImg;
	}

	public String getVipHeadImg() {
		return vipHeadImg;
	}
	public void setPersonalizedSignature(String personalizedSignature) {
		this.personalizedSignature = personalizedSignature;
	}

	public String getPersonalizedSignature() {
		return personalizedSignature;
	}
	public void setUseTag(Integer useTag) {
		this.useTag = useTag;
	}

	public Integer getUseTag() {
		return useTag;
	}
	public void setRsrvStr1(String rsrvStr1) {
		this.rsrvStr1 = rsrvStr1;
	}

	public String getRsrvStr1() {
		return rsrvStr1;
	}
	public void setRsrvStr3(String rsrvStr3) {
		this.rsrvStr3 = rsrvStr3;
	}

	public String getRsrvStr3() {
		return rsrvStr3;
	}
	public void setRsrvStr2(String rsrvStr2) {
		this.rsrvStr2 = rsrvStr2;
	}

	public String getRsrvStr2() {
		return rsrvStr2;
	}
	public void setRsrvNum1(Integer rsrvNum1) {
		this.rsrvNum1 = rsrvNum1;
	}

	public Integer getRsrvNum1() {
		return rsrvNum1;
	}
	public void setRsrvNum2(Integer rsrvNum2) {
		this.rsrvNum2 = rsrvNum2;
	}

	public Integer getRsrvNum2() {
		return rsrvNum2;
	}
	public void setRsrvNum3(Integer rsrvNum3) {
		this.rsrvNum3 = rsrvNum3;
	}

	public Integer getRsrvNum3() {
		return rsrvNum3;
	}
	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getQqId() {
		return qqId;
	}
	public void setQqNickname(String qqNickname) {
		this.qqNickname = qqNickname;
	}

	public String getQqNickname() {
		return qqNickname;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatId() {
		return wechatId;
	}
	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}

	public String getWechatNickname() {
		return wechatNickname;
	}
	public void setMicroblogId(String microblogId) {
		this.microblogId = microblogId;
	}

	public String getMicroblogId() {
		return microblogId;
	}
	public void setMicroblogNickname(String microblogNickname) {
		this.microblogNickname = microblogNickname;
	}

	public String getMicroblogNickname() {
		return microblogNickname;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getRegTime() {
		return regTime;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public String getBackImg() {
		return backImg;
	}
	public void setNicknameJianpin(String nicknameJianpin) {
		this.nicknameJianpin = nicknameJianpin;
	}

	public String getNicknameJianpin() {
		return nicknameJianpin;
	}
	public void setNameFirstJianpin(String nameFirstJianpin) {
		this.nameFirstJianpin = nameFirstJianpin;
	}

	public String getNameFirstJianpin() {
		return nameFirstJianpin;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}
	public void setIsTalent(Integer isTalent) {
		this.isTalent = isTalent;
	}

	public Integer getIsTalent() {
		return isTalent;
	}
	public void setTalentSettime(Date talentSettime) {
		this.talentSettime = talentSettime;
	}

	public Date getTalentSettime() {
		return talentSettime;
	}
	public void setWechatUnionid(String wechatUnionid) {
		this.wechatUnionid = wechatUnionid;
	}

	public String getWechatUnionid() {
		return wechatUnionid;
	}
	public void setChaqinOpenid(String chaqinOpenid) {
		this.chaqinOpenid = chaqinOpenid;
	}

	public String getChaqinOpenid() {
		return chaqinOpenid;
	}
	public void setAppMarketName(String appMarketName) {
		this.appMarketName = appMarketName;
	}

	public String getAppMarketName() {
		return appMarketName;
	}
	public void setIsFirstLogin4bind(Integer isFirstLogin4bind) {
		this.isFirstLogin4bind = isFirstLogin4bind;
	}

	public Integer getIsFirstLogin4bind() {
		return isFirstLogin4bind;
	}
	public void setRecomUserId(Integer recomUserId) {
		this.recomUserId = recomUserId;
	}

	public Integer getRecomUserId() {
		return recomUserId;
	}
	public void setRegSrcType(String regSrcType) {
		this.regSrcType = regSrcType;
	}

	public String getRegSrcType() {
		return regSrcType;
	}
	public void setRegSrc(String regSrc) {
		this.regSrc = regSrc;
	}

	public String getRegSrc() {
		return regSrc;
	}
}
