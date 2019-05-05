package com.chisondo.server.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.HttpStatus;
import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 短信 controller
 */
@RestController
public class SysSmsController extends AbstractController {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Autowired
    private RedisUtils redisUtils;

    private static final String VERIFY_CODE_PREFIX = "verify_code_";

    /**
     * 获取短信验证码
     *
     * @param req
     * @return
     */
    @RequestMapping("/api/rest/smscode")
    public CommonResp getVerifyCode(@RequestBody CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        if (ValidateUtils.isEmpty(jsonObj.get(Keys.PHONE))) {
            return CommonResp.error("手机号为空");
        }
//        int companyId = ValidateUtils.isEmpty("companyId") ? -1 : jsonObj.getIntValue("companyId");
//        int source = jsonObj.getIntValue("source"); 0-小程序，1-android,2-ios.
        String mobile = jsonObj.getString(Keys.PHONE);
        String message = this.generateMessage(mobile);
        Map<String, Object> params = ImmutableMap.of("operid", "chisondo", "operpass", "q6uHtfVBfJU=",
                "appendid", "001", "desmobile", mobile, "content", message);
        String result = this.restTemplateUtils.httpPostMediaTypeFromData("http://api.lancrm.com/SmsSend/submitmessage.ashx", String.class, params);
        JSONObject resp = JSONObject.parseObject(result);
        int code = resp.getIntValue("code");
        return new CommonResp(0 == code ? HttpStatus.SC_OK : HttpStatus.SC_INTERNAL_SERVER_ERROR, this.parseRetCode(code));
    }

    private String parseRetCode(int code) {
        switch (code) {
            case 1:
                return "账号不存在";
            case 2:
                return "密码错误";
            case 3:
                return "号码为空";
            case 4:
                return "内容为空";
            case 5:
                return "未知错误，联系客服";
            case 6:
                return "系统异常，联系管理员";
            case 7:
                return "扩展号格式错误";
            default:
                return "批量短信提交成功";
        }
    }

    /**
     * 验证短信验证码
     *
     * @param req
     * @return
     */
    @RequestMapping("/api/rest/verifycode")
    public CommonResp checkVerifyCode(@RequestBody CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        if (ValidateUtils.isEmpty(jsonObj.get(Keys.PHONE))) {
            return CommonResp.error("手机号为空");
        }
        if (ValidateUtils.isEmpty(jsonObj.get("code"))) {
            return CommonResp.error("验证码为空");
        }
        this.checkVerifyCodeFromRedis(jsonObj.getString(Keys.PHONE), jsonObj.getString("code"));
        return CommonResp.ok();
    }

    private String generateMessage(String mobile) {
        String verifyCode = CommonUtils.getValidationCode(6);
        this.saveVerifyCode2Redis(mobile, verifyCode);
        logger.error("随机验证码 = {}", verifyCode);
        String message = "【茶亲】亲爱的用户，您本次操作的验证码是" + verifyCode + "，3分钟内有效。";
        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码失败");
        }
        return message;
    }

    private void saveVerifyCode2Redis(String mobile, String verifyCode) {
        this.redisUtils.set(VERIFY_CODE_PREFIX + mobile, verifyCode, 180);
    }

    private void checkVerifyCodeFromRedis(String mobile, String verifyCode) {
        String cachedVerifyCode = this.redisUtils.get(VERIFY_CODE_PREFIX + mobile);
        if (ValidateUtils.isEmpty(cachedVerifyCode)) {
            throw new CommonException("错误的验证码");
        }
        if (ValidateUtils.notEquals(verifyCode, cachedVerifyCode)) {
            throw new CommonException("错误的验证码");
        }
    }


}
