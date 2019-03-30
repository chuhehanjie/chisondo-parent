package com.chisondo.server.modules.sys.controller;

import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CacheDataUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.sys.entity.SysConfigEntity;
import com.chisondo.server.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {

	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping("update")
	public CommonResp updateSysConfig(@RequestBody Map<String, Object> params){
		String key = params.get("key").toString();
		String value = params.get("value").toString();
		SysConfigEntity config = CacheDataUtils.getConfigByKey(key);
		if (ValidateUtils.isNotEmpty(config)) {
			config.setValue(value);
			// 更新数据库
			this.sysConfigService.update(config);
		}
		return CommonResp.ok();
	}

}
