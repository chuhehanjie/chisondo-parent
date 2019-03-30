package com.chisondo.server.common.exception;

import com.chisondo.server.common.http.CommonResp;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@RestControllerAdvice
public class CommonExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(CommonException.class)
	public CommonResp handleCommonException(CommonException e){
		return CommonResp.error(e.getCode(), e.getMsg());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public CommonResp handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return CommonResp.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public CommonResp handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return CommonResp.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public CommonResp handleException(Exception e){
		logger.error(e.getMessage(), e);
		return CommonResp.error(e.getMessage());
	}
}
