package com.chisondo.server.modules.tea.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.ParamValidator;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.validator.QryMyTeaSpectrumValidator;
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
 * 茶类信息查询 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
@RestController
public class TeaQueryController {

	@Autowired
	private AppTeaSortService appTeaSortService;

	@Autowired
	private AppChapuService appChapuService;
	/**
	 * 查询所有茶类信息
	 */
	@PostMapping("/api/rest/chapu/getsorts")
	public CommonResp queryAllTeaSort(@RequestBody CommonReq req){
		List<TeaSortRowDTO> teaSorts = this.appTeaSortService.queryAllTeaSorts();
		TeaSortQryDTO teaSortQryDTO = new TeaSortQryDTO();
		if (!CollectionUtils.isEmpty(teaSorts)) {
			Integer defaultSortId = teaSorts.get(0).getSortId();
			for (TeaSortRowDTO teaSort : teaSorts) {
				teaSort.setImgUrl(CommonUtils.plusFullImgPath(teaSort.getImgUrl()));
				if (ValidateUtils.equals(1, teaSort.getIsDefault())) {
					defaultSortId = teaSort.getSortId();
				}
			}
			teaSortQryDTO.setDefaultSortId(defaultSortId);
			teaSortQryDTO.setRows(teaSorts);
		}
		return CommonResp.ok(teaSortQryDTO);
	}

	/**
	 * 查询茶谱详情
	 */
	@PostMapping("/api/rest/chapu/detail")
	public CommonResp queryTeaSpectrumDetail(@RequestBody CommonReq req){
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		Integer chapuId = jsonObj.getInteger(Keys.CHAPU_ID);
		if (ValidateUtils.isEmpty(chapuId)) {
			throw new CommonException("茶谱ID为空");
		}
		QryTeaSpectrumDetailDTO teaSpectrumDetail = this.appChapuService.queryTeaSpectrumDetailById(chapuId);
		return CommonResp.ok(teaSpectrumDetail);
	}

	/**
	 * 根据条件查询茶谱列表
	 */
	@PostMapping("/api/rest/chapu/list")
	public CommonResp queryTeaSpectrumListByCondition(@RequestBody CommonReq req){
		List<QryTeaSpectrumDetailDTO> teaSpectrumDetails = this.appChapuService.queryTeaSpectrumListByCondition(req);
		return CommonResp.ok(ImmutableMap.of("rows", teaSpectrumDetails));
	}

	/**
	 * 搜索茶谱
	 */
	@PostMapping("/api/rest/chapu/search")
	public CommonResp searchTeaSpectrum(@RequestBody CommonReq req){
		List<QryTeaSpectrumDetailDTO> teaSpectrumDetails = this.appChapuService.searchTeaSpectrum(req);
		return CommonResp.ok(ImmutableMap.of("rows", teaSpectrumDetails));
	}

	/**
	 * 我的茶谱
	 */
	@PostMapping("/api/rest/chapu/mine")
	@ParamValidator(value = {QryMyTeaSpectrumValidator.class}, isQuery = true)
	public CommonResp queryMyTeaSpectrum(@RequestBody CommonReq req){
		List<QryTeaSpectrumDetailDTO> teaSpectrumDetails = this.appChapuService.queryMyTeaSpectrum(req);
		return CommonResp.ok(ImmutableMap.of("rows", teaSpectrumDetails));
	}

}
