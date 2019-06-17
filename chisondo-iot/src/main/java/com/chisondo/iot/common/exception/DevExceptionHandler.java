package com.chisondo.iot.common.exception;

import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DevExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(ReadTimeoutException.class)
	public void handleReadTimeoutException(ReadTimeoutException e){
		logger.error("读取数据超时!", e);
		throw e;
	}

}
