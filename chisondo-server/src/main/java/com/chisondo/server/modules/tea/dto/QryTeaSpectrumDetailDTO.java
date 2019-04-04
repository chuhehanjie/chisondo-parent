package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;
import java.util.List;

public class QryTeaSpectrumDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private	String	phoneNum; // 创建人手机号码
    private	String	nickname; // 创建人昵称
    private	String	avatar; // 创建人图像
    private	String	chapuName; // 茶谱名称
    private	String	chapuImg; // 茶谱图标
    private	Integer	sortId; // 茶类ID
    private	String	sortName; // 茶类名称
    private	String	shapeName; // 茶形名称
    private	Integer	topFlag; // 置顶标识
    private	String	authAttr; // 鉴定属性
    private	Integer	standard; // 是否标准茶谱
    private	String	createTime; // 茶谱创建时间
    private	Integer	useNum; // 沏茶使用次数
    private	String	brandName; // 茶品牌名称
    private	Integer	amount; // 投茶量
    private	Integer	awake; // 是否醒茶

    private List<QryTeaSpectrumParamDTO> parameter;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getChapuName() {
        return chapuName;
    }

    public void setChapuName(String chapuName) {
        this.chapuName = chapuName;
    }

    public String getChapuImg() {
        return chapuImg;
    }

    public void setChapuImg(String chapuImg) {
        this.chapuImg = chapuImg;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public Integer getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Integer topFlag) {
        this.topFlag = topFlag;
    }

    public String getAuthAttr() {
        return authAttr;
    }

    public void setAuthAttr(String authAttr) {
        this.authAttr = authAttr;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAwake() {
        return awake;
    }

    public void setAwake(Integer awake) {
        this.awake = awake;
    }

    public List<QryTeaSpectrumParamDTO> getParameter() {
        return parameter;
    }

    public void setParameter(List<QryTeaSpectrumParamDTO> parameter) {
        this.parameter = parameter;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
