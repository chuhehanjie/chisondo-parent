package com.chisondo.server.common.interceptor;

import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CacheDataUtils;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.sys.entity.CompanyEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 未开启鉴权或是不需要鉴权的请求，则直接放行
        if (!this.isAuthEnable() || this.isReqExcludeAuth(request)) {
            return true;
        }
        String acckey = request.getHeader(Keys.ACCKEY);
        String reqsrc = request.getHeader(Keys.REQSRC);
        String timestamp = request.getHeader(Keys.TIMESTAMP);
        if (ValidateUtils.isEmptyString(acckey) || ValidateUtils.isEmptyString(reqsrc) || ValidateUtils.isEmptyString(timestamp)) {
            CommonUtils.outJSONResponse(response, CommonResp.error(HttpStatus.SC_BAD_REQUEST, "错误的请求参数！"));
            return false;
        }
        String actualAuthKey = this.getAccKey(reqsrc);
        if (ValidateUtils.isEmptyString(actualAuthKey)) {
            CommonUtils.outJSONResponse(response, CommonResp.error(HttpStatus.SC_UNAUTHORIZED, "授权key不存在！"));
            return false;
        }
        // 时间戳如果大于 当前时间5分钟会返回“请求超时”
        if (this.isReqTimeout(timestamp)) {
            CommonUtils.outJSONResponse(response, CommonResp.error(HttpStatus.SC_REQUEST_TIMEOUT, "请求超时！"));
            return false;
        }
//        System.currentTimeMillis()
        if (!this.doAuthentication(acckey, reqsrc, timestamp, actualAuthKey)) {
            CommonUtils.outJSONResponse(response, CommonResp.error(HttpStatus.SC_UNAUTHORIZED, "鉴权失败！"));
            return false;
        }
        return true;
    }

    private boolean isReqExcludeAuth(HttpServletRequest request) {
        String excludeAuthURL = CacheDataUtils.getConfigValueByKey(Keys.EXCLUDE_AUTH_URL);
        if (ValidateUtils.isNotEmptyString(excludeAuthURL)) {
            String[] excludeAuthURLs = excludeAuthURL.split(",");
            for (String url : excludeAuthURLs) {
                if (request.getRequestURI().contains(url)) {
                    log.info("url [{}] no need auth", url);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断请求是否超时
     * @param timestamp
     * @return
     */
    private boolean isReqTimeout(String timestamp) {
        Long timeout = Long.valueOf(CacheDataUtils.getConfigValueByKey(Keys.REQ_TIME_OUT));
        Long reqTimestamp = Long.valueOf(timestamp);
        Long currentTimestamp = System.currentTimeMillis();
        if (Math.abs(reqTimestamp - currentTimestamp) > timeout) {
            log.error("请求超时了！请求时间：{}，当前时间：{}，超时时间：{}", reqTimestamp, currentTimestamp, timeout);
            return true;
        }
        return false;
    }

    private boolean doAuthentication(String acckey, String reqsrc, String timestamp, String actualAuthKey) {
        String timestampValue = this.parseTimestampByRule(timestamp);
        String md5Key = DigestUtils.md5DigestAsHex((reqsrc + DigestUtils.md5DigestAsHex((actualAuthKey + timestampValue).getBytes())).getBytes());
        return ValidateUtils.equals(acckey, md5Key);
    }

    private String parseTimestampByRule(String timestamp) {
        String authRule = CacheDataUtils.getConfigValueByKey(Keys.AUTH_RULE);
        String[] authRules = authRule.split("_");
        int startPos = Integer.valueOf(authRules[0]);
        int endPos = Integer.valueOf(authRules[1]);
        return timestamp.substring(startPos, endPos);
    }

    private String getAccKey(String reqsrc) {
        for (CompanyEntity company : CacheDataUtils.getCompanyList()) {
            if (ValidateUtils.equals(company.getId() + "", reqsrc)) {
                return company.getKey();
            }
        }
        return null;
    }

    private boolean isAuthEnable() {
        return ValidateUtils.equals("true", CacheDataUtils.getConfigValueByKey(Keys.AUTH_FLAG));
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        String timestamp = (System.currentTimeMillis() + "").substring(1,4);
        System.out.println(timestamp);
        String key = "XBcfMWOTUygYQFCYlvhMDmcmQNaEXWPu";
        int srcreq = 1;
        String acckey = DigestUtils.md5DigestAsHex((srcreq + DigestUtils.md5DigestAsHex((key + timestamp).getBytes())).getBytes());
        System.out.println(acckey);
        System.out.println(acckey.equalsIgnoreCase("4e8f82dc867c4f8161c6311b1fb8fa57"));
        // 4e8f82dc867c4f8161c6311b1fb8fa57
    }
}
