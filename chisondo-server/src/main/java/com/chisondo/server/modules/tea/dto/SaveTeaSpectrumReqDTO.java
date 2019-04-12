package com.chisondo.server.modules.tea.dto;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

public class SaveTeaSpectrumReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer operFlag; // 操作标志	0-创建茶谱 1-修改 2-保存（未创建）
    private Integer chapuId; // 茶谱ID	只在修改（operFlag为1）时传入，创建和保存 传空
    private String phoneNum; // 手机号码	设备绑定的手机号码
    private String nickname; // 用户昵称	可为空可为微信昵称
    private String avatar; // 用户图像	可为空可为微信图像
    private String chapuName; // 茶谱名称
    private String chapuImg; // 茶谱图标	可为空，显示默认图标
    private Integer sortId; // 茶类ID	参考“获取茶类”接口
    private String sortName; // 茶类名称
    private String brandName; // 茶品牌名称	茶叶所属品牌
    private Integer amount; // 投茶量		0~30,单位：克
    private Integer awake; // 是否醒茶		0-否；1-是
    private String desc; //	茶谱说明		茶谱介绍（预留）
    private Integer makeTimes; // 泡数		茶谱总泡数

    private List<QryTeaSpectrumParamDTO> parameter;

    public Integer getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(Integer operFlag) {
        this.operFlag = operFlag;
    }

    public Integer getChapuId() {
        return chapuId;
    }

    public void setChapuId(Integer chapuId) {
        this.chapuId = chapuId;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getMakeTimes() {
        return makeTimes;
    }

    public void setMakeTimes(Integer makeTimes) {
        this.makeTimes = makeTimes;
    }

    public List<QryTeaSpectrumParamDTO> getParameter() {
        return parameter;
    }

    public void setParameter(List<QryTeaSpectrumParamDTO> parameter) {
        this.parameter = parameter;
    }

    public static void main(String[] args) {
        SaveTeaSpectrumReqDTO req = new SaveTeaSpectrumReqDTO();
        req.setOperFlag(2);
        req.setChapuId(null);
        req.setPhoneNum("18975841003");
        req.setNickname("chris");
        req.setAvatar("test.jpg");
        req.setChapuName("超级茶谱");
        req.setChapuImg("20170522/pic/1495437432751401.png");
        req.setSortId(2);
        req.setSortName("绿茶");
        req.setBrandName("旺德府");
        req.setAmount(25);
        req.setAwake(0);
        req.setDesc("");
        req.setMakeTimes(5);
        List<QryTeaSpectrumParamDTO> params = ImmutableList.of(new QryTeaSpectrumParamDTO(250, 55, 88), new QryTeaSpectrumParamDTO(380, 77, 186));
        req.setParameter(params);
        System.out.println(JSONObject.toJSONString(req));
        String json = "{\"amount\":25,\"avatar\":\"test.jpg\",\"awake\":0,\"brandName\":\"旺德府\",\"chapuImg\":\"20170522/pic/1495437432751401.png\",\"chapuName\":\"超级茶谱\",\"desc\":\"\",\"makeTimes\":5,\"nickname\":\"chris\",\"operFlag\":2,\"parameter\":[{\"dura\":88,\"temp\":55,\"water\":250},{\"dura\":186,\"temp\":77,\"water\":380}],\"phoneNum\":\"18975841003\",\"sortId\":2,\"sortName\":\"绿茶\"}";
    }
}
