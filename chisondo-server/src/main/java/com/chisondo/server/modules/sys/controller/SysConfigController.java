package com.chisondo.server.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CacheDataUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.sys.entity.StarbannerEntity;
import com.chisondo.server.modules.sys.entity.SysConfigEntity;
import com.chisondo.server.modules.sys.service.StarbannerService;
import com.chisondo.server.modules.sys.service.SysConfigService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SysConfigController extends AbstractController {

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private StarbannerService starbannerService;

	@RequestMapping("/sys/config/update")
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

	/**
	 * 查询启动页广告图信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qrystartbanner")
	@DataSource(name = DataSourceNames.SECOND)
	public CommonResp queryStartAdBanner(@RequestBody CommonReq req){
		JSONObject jsonObject = JSONObject.parseObject(req.getBizBody());
		Map<String, Object> params = Maps.newHashMap();
		if (ValidateUtils.isNotEmpty(jsonObject.get("companyId"))) {
			params.put("companyId", jsonObject.getInteger("companyId"));
		}
		if (ValidateUtils.isNotEmpty(jsonObject.get("isshow"))) {
			params.put("isShow", jsonObject.getInteger("isshow"));
		}
		List<StarbannerEntity> resultList = this.starbannerService.queryList(params);
		return CommonResp.ok(resultList);
	}



}
