package com.chisondo.server.modules.tea.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.chisondo.server.modules.tea.service.TeaStatisticsShareService;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;




/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
@RestController
@RequestMapping("/tea/teastatisticsshare")
public class TeaStatisticsShareController {
	@Autowired
	private TeaStatisticsShareService teaStatisticsShareService;
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	public CommonResp list(@RequestBody Map<String, Object> params){
        return new CommonResp();
	}

}
