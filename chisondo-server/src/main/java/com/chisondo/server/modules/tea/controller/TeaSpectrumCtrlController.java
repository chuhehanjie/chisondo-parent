package com.chisondo.server.modules.tea.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.DevOperateLog;
import com.chisondo.server.common.annotation.ParamValidator;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.device.validator.DelOrFinishTeaSpectrumValidator;
import com.chisondo.server.modules.device.validator.QryMyTeaSpectrumValidator;
import com.chisondo.server.modules.device.validator.TeaSpectrumExistenceValidator;
import com.chisondo.server.modules.device.validator.UserExistenceValidator;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumDetailDTO;
import com.chisondo.server.modules.tea.dto.TeaSortQryDTO;
import com.chisondo.server.modules.tea.dto.TeaSortRowDTO;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.chisondo.server.modules.tea.service.AppTeaSortService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 茶谱控制 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Apr 10.19
 */
@RestController
public class TeaSpectrumCtrlController {

	@Autowired
	private AppTeaSortService appTeaSortService;

	@Autowired
	private AppChapuService appChapuService;

	/**
	 * 删除我的茶谱/结束茶谱泡茶
	 */
	@PostMapping("/api/rest/chapu/delfinish")
	@ParamValidator(value = {UserExistenceValidator.class, DelOrFinishTeaSpectrumValidator.class},isQuery = true)
	@DevOperateLog("删除我的茶谱/结束茶谱泡茶")
	public CommonResp delOrFinishTeaSpectrum(@RequestBody CommonReq req){
		return this.appChapuService.delOrFinishTeaSpectrum(req);
	}


}
